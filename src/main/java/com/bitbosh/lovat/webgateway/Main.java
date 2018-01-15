package com.bitbosh.lovat.webgateway;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.Timer;
import java.util.TimerTask;

import javax.script.ScriptEngineManager;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.ws.rs.client.Client;

import io.dropwizard.auth.AuthValueFactoryProvider;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;

import com.bitbosh.lovat.webgateway.api.ChatResource;
import com.bitbosh.lovat.webgateway.api.NashornController;
import com.bitbosh.lovat.webgateway.auth.CustomAuthenticator;
import com.bitbosh.lovat.webgateway.core.NotAuthorizedExceptionHandler;
import com.bitbosh.lovat.webgateway.core.User;
import com.bitbosh.lovat.webgateway.resources.WebGatewayResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.websockets.WebsocketBundle;
import jdk.nashorn.api.scripting.NashornScriptEngine;

@SuppressWarnings("restriction")
public class Main extends Application<ApplicationConfiguration> {
	
	public static void main(String[] args) throws Exception {
		new Main().run(args);
	}

	@Override
	public void run(ApplicationConfiguration configuration, Environment environment) throws URISyntaxException {

		// Create a DBIFactory to build instances of Dao classes for each
		// Resource
		// in the application.
		final DBIFactory factory = createDbiFactory();

		// The database configuration details are read from the DataSourcFactory
		// within the
		// MainConfiguration class.
		final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
		final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration()).using(environment).build("client");
		final NashornScriptEngine nashorn = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
		final NashornController react = new NashornController(nashorn);

		// Register each Resource with jersey and pass in the Dao so that it can
		// interact with the database.
		JerseyEnvironment jerseyEnvironment = environment.jersey();
		jerseyEnvironment.register(new WebGatewayResource(jdbi, client, react));				
		jerseyEnvironment.register(NotAuthorizedExceptionHandler.class);

		// Authenticator
		jerseyEnvironment.register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>().setAuthenticator(new CustomAuthenticator(jdbi)).setRealm("BASIC-AUTH-REALM").buildAuthFilter()));
        jerseyEnvironment.register(new AuthValueFactoryProvider.Binder<>(User.class));

		// Enable CORS headers
		final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

		// Configure CORS parameters
		cors.setInitParameter("allowedOrigins", "*");
		cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
		cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

		// Add URL mapping
		cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

		// poll the healthcheck service every 10 minutes
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				String healthCheckEndpoint = "https://hchk.io/bcdcc4ba-c912-4534-aef8-c8f4e3a15e16";
				String healthCheckApiKey = System.getenv("HEALTH_CHECK_API_KEY");
				HttpClient client = HttpClientBuilder.create().build();
				HttpGet httpGet = new HttpGet(healthCheckEndpoint);
				httpGet.setHeader("X-Api-Key", healthCheckApiKey);
				HttpResponse response = null;
				try {
					response = client.execute(httpGet);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				HttpEntity entity = response.getEntity();
				String result = entity.toString();
				System.out.println("GET " + healthCheckEndpoint);
			}

		}, 0, 300000);		
	}

	private DBIFactory createDbiFactory() {
		return new DBIFactory();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize(Bootstrap<ApplicationConfiguration> configuration) {
		configuration.addBundle(new AssetsBundle("/assets", "/assets"));
		configuration.addBundle(new ViewBundle());		
		configuration.addBundle(new WebsocketBundle(ChatResource.class));
	}
}