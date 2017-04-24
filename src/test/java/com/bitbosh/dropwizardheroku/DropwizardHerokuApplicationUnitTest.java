package com.bitbosh.DropwizardHeroku;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import com.bitbosh.DropwizardHeroku.api.EventResource;

import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

public class DropwizardHerokuApplicationUnitTest {

  @Test
  public void main_verifyMainCall_IfApplicationStarted() throws Exception {
    String expected = "test";

    // Mock the ctor for DropwizardHerokuApplication super class Application<DropwizardHerokuConfiguration>
    new MockUp<Application<DropwizardHerokuConfiguration>>() {

      @Mock
      public void $init() {

      }
    };

    // Mock the ctor for DropwizardHerokuApplication
    new MockUp<DropwizardHerokuApplication>() {

      @Mock
      public void run(String... args) {
        String actual = args[0];

        // Assert args contains expected when called from main()
        assertTrue(expected.equals(actual));
      }

    };

    DropwizardHerokuApplication.main(new String[] { expected });
  }

  @Test
  public void run_verifyRunCall_IfApplicationStarted(@Mocked DropwizardHerokuConfiguration configuration,
      @Mocked Environment environment, @Mocked JerseyEnvironment jerseyEnv) throws Exception {

    // Mock the ctor for DropwizardHerokuApplication super class Application<DropwizardHerokuConfiguration>
    new MockUp<Application<DropwizardHerokuConfiguration>>() {

      @Mock
      public void $init() {
      }
    };

    new MockUp<JerseyEnvironment>() {

      @Mock
      public void register(Object component) {
      }
    };

    new MockUp<Environment>() {
      @Mock
      public JerseyEnvironment jersey() {
        return jerseyEnv;
      }
    };

    new MockUp<DBIFactory>() {

      @Mock
      public DBI build(Environment environment, PooledDataSourceFactory configuration, String name) {
        return null;
      }
    };

    new MockUp<EventResource>() {
      @Mock
      public void $init(DBI jdbi) {
      }
    };

    DropwizardHerokuApplication app = new DropwizardHerokuApplication();
    app.run(configuration, environment);
  }
}
