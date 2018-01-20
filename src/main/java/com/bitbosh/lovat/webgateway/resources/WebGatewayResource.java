package com.bitbosh.lovat.webgateway.resources;

import com.bitbosh.lovat.webgateway.api.ApiResponse;
import com.bitbosh.lovat.webgateway.api.NashornController;
import com.bitbosh.lovat.webgateway.core.User;
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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("unchecked")
@Path("/")
public class WebGatewayResource {

	private final Client client;
    private final HttpClient httpClient = HttpClientBuilder.create().build();

	private NashornController nashornController;

	private final String kServerRenderFunctionIndex = "renderServerIndex";
	private final String kServerRenderFunctionDashboard = "renderServerDashboard";
	private final String kServerRenderFunctionLogin = "renderServerLogin";

    private final String kEventServiceUrl = "https://dropwizardheroku-event-service.herokuapp.com";
    private final String kEventServiceApiEndpointEvents = kEventServiceUrl + "/v1/api/events";

	public WebGatewayResource(Client client, NashornController nashornController) {
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
		return new LoginView(loginViewHtml);
	}

    @GET
    @Path("/logout")
    public Response logout(@Context HttpServletRequest request) {
        return Response.seeOther(URI.create("/")).cookie(new NewCookie(HttpHeaders.AUTHORIZATION, "")).header(HttpHeaders.AUTHORIZATION, "").build();
    }

	@POST
	@Path("/auth")
	public Response authenticate(@Auth User user) {
        final String base64 = Base64.encodeAsString(user.getUsername() + ":" + user.getPassword());
        return Response.status(Response.Status.CREATED)
                .cookie(new NewCookie(HttpHeaders.AUTHORIZATION, "Basic " + base64))
                .build();
	}

	@GET
	@Path("/dashboard")
	@Produces(MediaType.TEXT_HTML)
	public DashboardView dashboard(@Auth User user) {

		ApiResponse events = getEvents();
        ApiResponse tweets = getTweets();
        ApiResponse assetPair = getKrakenTickerData();

        List<Object> eventsData = (List<Object>) events.getList();
        List<Object> tweetsData = (List<Object>) tweets.getList();
        List<Object> assetPairData = (List<Object>) assetPair.getList();

        String dashboardViewHtml = this.nashornController.renderReactJsComponent(kServerRenderFunctionDashboard, eventsData, tweetsData, assetPairData);
        return new DashboardView(dashboardViewHtml);
	}

	@GET
	@Path("/events")
	@Produces(MediaType.APPLICATION_JSON)
	public ApiResponse getEvents() {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Invocation.Builder invocationBuilder = buildRequest();
            Response response = invocationBuilder.get();
            apiResponse = response.readEntity(ApiResponse.class);
        } catch (Exception e) {
            System.out.println("ERROR getEvents()");
        }

		return apiResponse;
	}

	private Invocation.Builder buildRequest() {
        WebTarget webTarget = this.client.target(kEventServiceApiEndpointEvents);
        return webTarget.request(MediaType.APPLICATION_JSON);
    }

	@POST
	@Path("/events")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResponse createEvent(String jsonObject) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Invocation.Builder invocationBuilder = buildRequest();
            Response response = invocationBuilder.post(Entity.entity(jsonObject, MediaType.APPLICATION_JSON));
            apiResponse = response.readEntity(ApiResponse.class);
        } catch (Exception e) {
            System.out.println("ERROR getEvents()");
        }

		return apiResponse;
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
	public ApiResponse getTweets() {

		String bearerToken = System.getenv("TWITTERAPI_ACCESS_TOKEN");
		HttpGet httpGet = new HttpGet("https://api.twitter.com/1.1/search/tweets.json");
		httpGet.setHeader("Authorization", "Bearer " + bearerToken);
		httpGet.setHeader("Content-Type", "application/json");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("q", "#blockchain"));
		params.add(new BasicNameValuePair("src", "tyah"));
        HashMap<String, Object> statuses = makeRequest(httpGet, params);
		List<LinkedHashMap<String, Object>> tweets = (List<LinkedHashMap<String, Object>>) statuses.get("statuses");
		return new ApiResponse(tweets);
	}

	private HashMap<String, Object> makeRequest(HttpGet httpGet, List<NameValuePair> params) {

        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            URI uri = new URIBuilder(httpGet.getURI()).addParameters(params).build();
            httpGet.setURI(uri);
            HttpResponse response = this.httpClient.execute(httpGet);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(reader, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (Exception e) {

        }
        return result;
    }

	@GET
	@Path("/kraken")
	@Produces(MediaType.APPLICATION_JSON)
	public ApiResponse getKrakenTickerData() {

		HttpGet httpGet = new HttpGet("https://api.kraken.com/0/public/Ticker");
		httpGet.setHeader("Content-Type", "application/json");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("pair", "BTCEUR,ETHEUR,XRPEUR,LTCEUR,XMREUR"));
        HashMap<String, Object> krakenApiResponse = makeRequest(httpGet, params);
		HashMap<String, Object> result = (HashMap<String, Object>) krakenApiResponse.get("result");
		List<HashMap<String, Object>> assetPairs = new ArrayList<HashMap<String, Object>>();
		if(result != null) {
            result.forEach((key, value) -> {
                HashMap<String, Object> jsonObject = (HashMap<String, Object>) value;
                jsonObject.put("pair_name", key);
                assetPairs.add(jsonObject);
            });
        }

		return new ApiResponse(assetPairs);
	}
}
