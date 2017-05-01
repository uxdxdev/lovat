package com.bitbosh.dropwizardheroku.webgateway.api;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import com.bitbosh.dropwizardheroku.webgateway.core.Microservice;
import com.bitbosh.dropwizardheroku.webgateway.repository.WebGatewayDao;
import com.bitbosh.dropwizardheroku.webgateway.views.IndexView;

import mockit.Expectations;
import mockit.Mocked;

public class WebGatewayResourceUnitTest {

	@Test
	public void EventResource_constructsSuccessfully_IfEventDaoNotNull(@Mocked DBI jdbi, @Mocked WebGatewayDao eventDao,
			@Mocked Client client, @Mocked React react) {

		new Expectations() {
			{
				jdbi.onDemand(withAny(WebGatewayDao.class));
				result = eventDao; // return valid EventDao
			}
		};

		WebGatewayResource eventResource = new WebGatewayResource(jdbi, client, react);
	}

	@Test
	public void getEvents_returnsCorrectApiResponce_IfEventsListRequested(@Mocked DBI jdbi, @Mocked Client client,
			@Mocked WebTarget webTarget, @Mocked Invocation.Builder invocationBuilder, @Mocked Response response,
			@Mocked ApiResponse apiResponse, @Mocked React react) {

		new Expectations() {
			{
				client.target(Microservice.kEventsApiEndpointEvents);
				result = webTarget;

				webTarget.request(MediaType.APPLICATION_JSON);
				result = invocationBuilder;

				invocationBuilder.get();
				result = response;

				response.readEntity(withAny(ApiResponse.class));
				result = apiResponse;
			}
		};

		WebGatewayResource webGatewayResource = new WebGatewayResource(jdbi, client, react);
		ApiResponse actualResponse = webGatewayResource.getEvents();
		assertEquals(apiResponse, actualResponse);
	}

	@Test
	public void index_returnsCorrectHtml_IfInvokedWithValidDependencies(@Mocked DBI jdbi, @Mocked Client client, @Mocked WebTarget webTarget,
			@Mocked Invocation.Builder invocationBuilder, @Mocked Response response, @Mocked React react) throws IOException {

		String expectedHtml = "<div>test</div>";
		new Expectations() {
			List<Object> eventsProps;
			ApiResponse apiResponse = new ApiResponse();
			{
				client.target(Microservice.kEventsApiEndpointEvents);
				result = webTarget;

				webTarget.request(MediaType.APPLICATION_JSON);
				result = invocationBuilder;

				invocationBuilder.get();
				result = response;

				response.readEntity(withAny(ApiResponse.class));
				result = apiResponse;

				react.renderComponent(anyString, eventsProps);
				result = expectedHtml;
			}
		};

		WebGatewayResource webGatewayResource = new WebGatewayResource(jdbi, client, react);
		IndexView actualHtml = webGatewayResource.index();
		//assertEquals(expectedHtml, actualHtml);
	}

}
