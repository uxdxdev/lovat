package com.bitbosh.dropwizardheroku.webgateway.api;

import javax.ws.rs.client.Client;

import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import com.bitbosh.dropwizardheroku.webgateway.repository.WebGatewayDao;

import mockit.Expectations;
import mockit.Mocked;

public class WebGatewayResourceUnitTest {
	@Test
	public void EventResource_constructsSuccessfully_IfEventDaoNotNull(@Mocked DBI jdbi,
			@Mocked WebGatewayDao eventDao, @Mocked Client client) {

		new Expectations() {
			{
				jdbi.onDemand(withAny(WebGatewayDao.class));
				result = eventDao; // return valid EventDao
			}
		};

		WebGatewayResource eventResource = new WebGatewayResource(jdbi, client);
	}
}
