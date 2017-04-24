package com.bitbosh.DropwizardHeroku;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class DropwizardHerokuConfiguration extends Configuration {

  @Valid
  @NotNull
  private DataSourceFactory database = new DataSourceFactory();

  /**
   * A getter for the database factory.
   *
   * @return An instance of database factory deserialized from the configuration file passed as a command-line argument
   *         to the application.
   * @throws URISyntaxException
   */
  @JsonProperty("database")
  public DataSourceFactory getDataSourceFactory() throws URISyntaxException {

    // Overwrite the value read from the database property of the config.yml file and use the value provided by Heroku
    // environment.
    //
    // When running the app locally using `heroku local` the remote postgres db url needs to be added to the IDE or to
    // your environment. Run `heroku config` to get the environment variable and its value. For local dev with Eclipse
    // add DATABASE_URL and its value as a parameter in the Environment tab. For running the application with Heroku cli
    // add DATABASE_URL and its value to the .bash_profile file and either restart your terminal or source it, i.e.
    // source ~/.bash_profile
    //
    // For reference:
    // https://devcenter.heroku.com/articles/connecting-to-relational-databases-on-heroku-with-java#connecting-to-a-database-remotely
    URI dbUri = null;
    final String databaseUrl = System.getenv("DATABASE_URL");
    if (databaseUrl != null) {
      dbUri = new URI(databaseUrl);
    }

    if (dbUri != null) {
      String username = dbUri.getUserInfo().split(":")[0];
      String password = dbUri.getUserInfo().split(":")[1];
      String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()
          + "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

      this.database.setUser(username);
      this.database.setPassword(password);
      this.database.setUrl(dbUrl);
    }
    return database;
  }

  /**
   * Needed to set the database factory when the config.yml file is loaded.
   * 
   * @param dataSourceFactory
   */
  @JsonProperty("database")
  public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
    this.database = dataSourceFactory;
  }
}
