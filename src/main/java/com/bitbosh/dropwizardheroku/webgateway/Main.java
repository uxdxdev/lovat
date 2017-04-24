package com.bitbosh.dropwizardheroku.webgateway;

import java.net.URISyntaxException;

import org.skife.jdbi.v2.DBI;

import com.bitbosh.dropwizardheroku.webgateway.api.WebGatewayResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Main extends Application<ApplicationConfiguration> {

  public static void main(String[] args) throws Exception {
    new Main().run(args);
  }

  @Override
  public void run(ApplicationConfiguration configuration, Environment environment) throws URISyntaxException {

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
    jerseyEnvironment.register(new WebGatewayResource(jdbi));
  }

  private DBIFactory createDbiFactory() {
    return new DBIFactory();
  }

  @Override
  public void initialize(Bootstrap<ApplicationConfiguration> configuration) {
    configuration.addBundle(new AssetsBundle("/assets", "/", "index.html"));
  }
}