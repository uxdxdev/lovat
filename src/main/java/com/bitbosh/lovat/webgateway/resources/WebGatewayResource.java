package com.bitbosh.lovat.webgateway.resources;

import com.bitbosh.lovat.webgateway.api.ApiResponse;
import com.bitbosh.lovat.webgateway.api.NashornController;
import com.bitbosh.lovat.webgateway.core.User;
import com.bitbosh.lovat.webgateway.repository.UserDao;
import com.bitbosh.lovat.webgateway.views.DashboardView;
import com.bitbosh.lovat.webgateway.views.IndexView;
import com.bitbosh.lovat.webgateway.views.LoginView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.auth.Auth;
import io.dropwizard.jersey.params.LongParam;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.glassfish.jersey.internal.util.Base64;
import org.skife.jdbi.v2.DBI;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("unchecked")
@Path("/")
public class WebGatewayResource {

	private final UserDao userDao;

	private final Client client;

	private NashornController nashornController;

	private final String kServerRenderFunctionIndex = "renderServerIndex";
	private final String kServerRenderFunctionDashboard = "renderServerDashboard";
	private final String kServerRenderFunctionLogin = "renderServerLogin";

	static final String kUserServiceUrl = "https://dropwizardheroku-user-service.herokuapp.com";
	static final String kUserServiceApiEndpointRegister = kUserServiceUrl + "/v1/api/users";

	static final String kEventServiceUrl = "https://dropwizardheroku-event-service.herokuapp.com";
	static final String kEventServiceApiEndpointEvents = kEventServiceUrl + "/v1/api/events";

	// TODO implement Twitter Api service
	static final String kTwitterApiServiceUrl = "https://twitterapi-service.herokuapp.com";
	static final String kTwitterApiServiceApiEndpointTweets = kTwitterApiServiceUrl + "/v1/api/tweets";

	public WebGatewayResource(DBI jdbi, Client client, NashornController nashornController) {
		this.userDao = jdbi.onDemand(UserDao.class);
		this.client = client;
		this.nashornController = nashornController;
	}

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public IndexView index() {
		String indexViewHtml = this.nashornController.renderReactJsComponent(kServerRenderFunctionIndex);
		IndexView index = new IndexView(indexViewHtml);
		return index;
	}

	@GET
	@Path("/login")
	@Produces(MediaType.TEXT_HTML)
	public LoginView login() {
		String loginViewHtml = this.nashornController.renderReactJsComponent(kServerRenderFunctionLogin);
		LoginView login = new LoginView(loginViewHtml);
		return login;
	}

    @GET
    @Path("/logout")
    public Response logout(@Context HttpServletRequest request) {
	    if(request.getCookies() != null && request.getCookies().length > 0) {
	        System.out.println("Logging out... deleting cookies... " + request.getCookies().length);
            for (Cookie cookie : request.getCookies()) {
                cookie.setValue("");
                cookie.setMaxAge(0);
                cookie.setPath("/");
            }
        }

        // respond and reset the authorization header details
        return Response.seeOther(URI.create("/login"))
                .cookie(new NewCookie(HttpHeaders.AUTHORIZATION, ""))
                .header(HttpHeaders.AUTHORIZATION, "")
                .build();
    }

	@POST
	@Path("/auth")
	public Response authenticate(@Context HttpServletRequest request, @Auth User user) {

        final String base64 = Base64.encodeAsString(user.getUsername() + ":" + user.getPassword());
        return Response.status(Response.Status.CREATED)
                .cookie(new NewCookie(HttpHeaders.AUTHORIZATION, "Basic " + base64))
                .build();
	}

	@GET
	@Path("/dashboard")
	@Produces(MediaType.TEXT_HTML)
	public DashboardView dashboard(@Auth User user) {

		ApiResponse events = null;
        ApiResponse tweets = null;
        ApiResponse assetPair = null;
        try {
            events = getEvents();
            tweets = getTweets();
            assetPair = getKrakenTickerData();
            List<Object> eventsData = (List<Object>) events.getList();
            List<Object> tweetsData = (List<Object>) tweets.getList();
            List<Object> assetPairData = (List<Object>) assetPair.getList();
            String dashboardViewHtml = this.nashornController.renderReactJsComponent(kServerRenderFunctionDashboard, eventsData, tweetsData, assetPairData);


            return new DashboardView(dashboardViewHtml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new DashboardView("Services are spinning up, please wait");
	}

	@GET
	@Path("/events")
	@Produces(MediaType.APPLICATION_JSON)
	public ApiResponse getEvents() {
		WebTarget webTarget = this.client.target(kEventServiceApiEndpointEvents);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		ApiResponse apiResponse = response.readEntity(ApiResponse.class);
		return apiResponse;
	}

	@POST
	@Path("/events")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResponse createEvent(String jsonObject) {
		WebTarget webTarget = this.client.target(kEventServiceApiEndpointEvents);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(jsonObject, MediaType.APPLICATION_JSON));
		return response.readEntity(ApiResponse.class);
	}

	@DELETE
	@Path("/events/{id}")
	public ApiResponse deleteEventById(@PathParam("id") LongParam id) {
		WebTarget webTarget = this.client.target(kEventServiceApiEndpointEvents + "/" + id);
		Response response = webTarget.request().delete();
		return response.readEntity(ApiResponse.class);
	}

	@GET
	@Path("/tweets")
	@Produces(MediaType.APPLICATION_JSON)
	public ApiResponse getTweets() throws UnsupportedOperationException, IOException, URISyntaxException {

		// Call twitter api server
		// WebTarget webTarget =
		// this.client.target(kEventServiceApiEndpointEvents);
		// Invocation.Builder invocationBuilder =
		// webTarget.request(MediaType.APPLICATION_JSON);
		// Response response = invocationBuilder.get();
		// ApiResponse apiResponse = response.readEntity(ApiResponse.class);
		// return apiResponse;

		// TODO move this to a service of its own
		final HttpClient client = HttpClientBuilder.create().build();

		String bearerToken = System.getenv("TWITTERAPI_ACCESS_TOKEN");

		// create GET
		HttpGet httpGet = new HttpGet("https://api.twitter.com/1.1/search/tweets.json");
		httpGet.setHeader("Authorization", "Bearer " + bearerToken);
		httpGet.setHeader("Content-Type", "application/json");

		// create params
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("q", "#GameDev"));
		params.add(new BasicNameValuePair("src", "tyah"));

		// create endpoint with params
		URI uri = new URIBuilder(httpGet.getURI()).addParameters(params).build();
		// set uri to GET request
		httpGet.setURI(uri);

		// call twitter api
		HttpResponse response = client.execute(httpGet);
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		HashMap<String, Object> statuses = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		statuses = mapper.readValue(reader, new TypeReference<HashMap<String, Object>>() {
		});

		// get tweets from statues object
		List<LinkedHashMap<String, Object>> tweets = new ArrayList<LinkedHashMap<String, Object>>();
		tweets = (List<LinkedHashMap<String, Object>>) statuses.get("statuses");

		ApiResponse apiResponse = new ApiResponse(tweets);
		return apiResponse;
	}

	@GET
	@Path("/kraken")
	@Produces(MediaType.APPLICATION_JSON)
	public ApiResponse getKrakenTickerData() throws UnsupportedOperationException, IOException, URISyntaxException {

		// Call kraken api server
		// WebTarget webTarget =
		// this.client.target(kEventServiceApiEndpointEvents);
		// Invocation.Builder invocationBuilder =
		// webTarget.request(MediaType.APPLICATION_JSON);
		// Response response = invocationBuilder.get();
		// ApiResponse apiResponse = response.readEntity(ApiResponse.class);
		// return apiResponse;

		// TODO move this to a service of its own
		final HttpClient client = HttpClientBuilder.create().build();

		// create GET
		HttpGet httpGet = new HttpGet("https://api.kraken.com/0/public/Ticker");
		httpGet.setHeader("Content-Type", "application/json");

		// create params
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// TODO set parameter to pair from client
		params.add(new BasicNameValuePair("pair", "BTCEUR,ETHEUR,XRPEUR,LTCEUR,XMREUR"));

		// create endpoint with params
		URI uri = new URIBuilder(httpGet.getURI()).addParameters(params).build();
		// set uri to GET request
		httpGet.setURI(uri);

		// call twitter api
		HttpResponse response = client.execute(httpGet);
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		// convert json object to hashmap
		HashMap<String, Object> krakenApiResponse = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		krakenApiResponse = mapper.readValue(reader, new TypeReference<HashMap<String, Object>>() {
		});

		// get result object
		HashMap<String, Object> result = (HashMap<String, Object>) krakenApiResponse.get("result");

		// store the key in the object and add to the assetPairs list
		List<HashMap<String, Object>> assetPairs = new ArrayList<HashMap<String, Object>>();
		result.forEach((key, value) -> {
			HashMap<String, Object> jsonObject = (HashMap<String, Object>) value;
			jsonObject.put("pair_name", key);
			assetPairs.add(jsonObject);
		});

		ApiResponse apiResponse = new ApiResponse(assetPairs);
		return apiResponse;
	}
}
