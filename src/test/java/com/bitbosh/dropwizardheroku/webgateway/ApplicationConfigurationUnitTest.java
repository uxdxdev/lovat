package com.bitbosh.dropwizardheroku.webgateway;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import org.junit.Test;

import com.bitbosh.dropwizardheroku.webgateway.ApplicationConfiguration;

import io.dropwizard.db.DataSourceFactory;
import mockit.Expectations;
import mockit.Mocked;

public class ApplicationConfigurationUnitTest {
  ApplicationConfiguration applicationConfig = new ApplicationConfiguration();

  @Test
  public void getDataSourceFactory_returnsCorrectDataSourceFactory_IfCorrectDataSupplied(@Mocked final System unused)
      throws URISyntaxException {

    String expectedUser = "testUser";
    String expectedPassword = "testPassword";
    String host = "host";
    String port = "1234";
    String path = "/testPath";
    String hostPortString = host + ":" + port + path;
    String expectedDbUrl = "jdbc:postgresql://" + host + ':' + port + path
        + "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

    new Expectations() {
      {
        System.getenv(anyString);
        result = "postgres://" + expectedUser + ":" + expectedPassword + "@" + hostPortString;
      }
    };

    DataSourceFactory returnedDataSourceFactory = applicationConfig.getDataSourceFactory();
    String actualUser = returnedDataSourceFactory.getUser();
    String actualPassword = returnedDataSourceFactory.getPassword();
    String actualDbUrl = returnedDataSourceFactory.getUrl();

    assertEquals(expectedUser, actualUser);
    assertEquals(expectedPassword, actualPassword);
    assertEquals(expectedDbUrl, actualDbUrl);
  }

  @Test(expected = URISyntaxException.class)
  public void getDataSourceFactory_throwsURISyntaxException_IfUriCreationFails(@Mocked final System unused)
      throws URISyntaxException {
    String expectedUser = "testUser";
    String expectedPassword = "testPassword";
    String host = "host";
    String port = "1234";
    String path = "/testPath";
    String hostPortString = host + ":" + port + path;

    new Expectations() {
      {
        System.getenv(anyString);
        result = "incorrect,username://" + expectedUser + ":" + expectedPassword + "@" + hostPortString;
      }
    };

    // Uri creation fails when parsing db url
    DataSourceFactory returnedDataSourceFactory = applicationConfig.getDataSourceFactory();
  }

  @Test
  public void setDataSourceFactory_setsDataSourceFactoryCorrectly_IfSetExplicitlyUsingApi(@Mocked final System unused)
      throws URISyntaxException {
    DataSourceFactory dataSourceFactory = new DataSourceFactory();

    String expectedUser = "testUser";

    new Expectations() {
      {
        System.getenv(anyString);
        result = null;
      }
    };

    dataSourceFactory.setUser(expectedUser);
    applicationConfig.setDataSourceFactory(dataSourceFactory);

    DataSourceFactory returnedDataSourceFactory = applicationConfig.getDataSourceFactory();
    String actualUser = returnedDataSourceFactory.getUser();

    assertEquals(expectedUser, actualUser);
  }

}
