package com.bitbosh.DropwizardHeroku;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import org.junit.Test;

import io.dropwizard.db.DataSourceFactory;
import mockit.Expectations;
import mockit.Mocked;

public class DropwizardHerokuConfigurationUnitTest {
  DropwizardHerokuConfiguration dropwizardConfig = new DropwizardHerokuConfiguration();

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

    DataSourceFactory returnedDataSourceFactory = dropwizardConfig.getDataSourceFactory();
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
    DataSourceFactory returnedDataSourceFactory = dropwizardConfig.getDataSourceFactory();
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
    dropwizardConfig.setDataSourceFactory(dataSourceFactory);

    DataSourceFactory returnedDataSourceFactory = dropwizardConfig.getDataSourceFactory();
    String actualUser = returnedDataSourceFactory.getUser();

    assertEquals(expectedUser, actualUser);
  }

}
