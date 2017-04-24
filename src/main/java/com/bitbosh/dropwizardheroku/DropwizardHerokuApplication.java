package com.bitbosh.DropwizardHeroku;

import java.net.URISyntaxException;

import org.skife.jdbi.v2.DBI;

import com.bitbosh.DropwizardHeroku.api.EventResource;
import com.bitbosh.DropwizardHeroku.api.ExampleResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropwizardHerokuApplication extends Application<DropwizardHerokuConfiguration> {

  public static void main(String[] args) throws Exception {
    new DropwizardHerokuApplication().run(args);
  }

  @Override
  public void run(DropwizardHerokuConfiguration configuration, Environment environment) throws URISyntaxException {

    // Create a DBIFactory to build instances of Dao classes for each Resource
    // in the application.
    final DBIFactory factory = createDbiFactory();

    // The database configuration details are read from the DataSourcFactory
    // within the
    // MainConfiguration class.
    final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");

    // Register each Resource with jersey and pass in the Dao so that it can
    // interact with the database.
    JerseyEnvironment jerseyEnvironment = environment.jersey();
    jerseyEnvironment.register(new ExampleResource());
    jerseyEnvironment.register(new EventResource(jdbi));
  }

  private DBIFactory createDbiFactory() {
    return new DBIFactory();
  }

  @Override
  public void initialize(Bootstrap<DropwizardHerokuConfiguration> configuration) {
    configuration.addBundle(new AssetsBundle("/assets", "/"));
  }
}