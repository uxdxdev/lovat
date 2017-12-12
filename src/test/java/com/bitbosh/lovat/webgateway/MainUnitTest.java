package com.bitbosh.lovat.webgateway;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.dropwizard.Application;
import io.dropwizard.Bundle;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

public class MainUnitTest {

  @Test
  public void main_verifyMainCall_IfApplicationStarted() throws Exception {
    String expected = "test";

    // Mock the ctor for DropwizardHerokuApplication super class Application<DropwizardHerokuConfiguration>
    new MockUp<Application<ApplicationConfiguration>>() {

      @Mock
      public void $init() {

      }
    };

    // Mock the ctor for DropwizardHerokuApplication
    new MockUp<Main>() {

      @Mock
      public void run(String... args) {
        String actual = args[0];

        // Assert args contains expected when called from main()
        assertEquals(expected, actual);
      }

    };

    Main.main(new String[] { expected });
  }

//  @Test
//  public void run_verifyRunCall_IfApplicationStarted(
//      @Mocked Environment environment, 
//      @Mocked JerseyEnvironment jerseyEnv, 
//      @Mocked JerseyClientBuilder clientBuilder, 
//      @Mocked Client client, 
//      @Mocked ScriptEngine engine,
//      @Mocked ServletEnvironment servletEnv, 
//      @Mocked FilterRegistration.Dynamic dynFilter) throws Exception {
//
//    // Mock the ctor for DropwizardHerokuApplication super class Application<DropwizardHerokuConfiguration>
//    new MockUp<Application<ApplicationConfiguration>>() {
//
//      @Mock
//      public void $init() {
//      }
//    };
//
//    new MockUp<JerseyEnvironment>() {
//
//      @Mock
//      public void register(Object component) {
//      }
//    };
//
//    new MockUp<Environment>() {
//        @Mock
//        public JerseyEnvironment jersey() {
//          return jerseyEnv;
//        }
//        
//        @Mock
//        public ServletEnvironment servlets() {
//      	  return servletEnv;
//        }
//      };
//      
//      new MockUp<ServletEnvironment>() {        
//          @Mock
//          public FilterRegistration.Dynamic addFilter(String name, Class<? extends Filter> klass) {
//  			return dynFilter;        
//          }
//        };
//
//    new MockUp<DBIFactory>() {
//
//      @Mock
//      public DBI build(Environment environment, PooledDataSourceFactory configuration, String name) {
//        return null;
//      }
//    };
//
//    new MockUp<WebGatewayResource>() {
//      @Mock
//      public void $init(DBI jdbi, Client client, NashornController react) {
//      }
//    };
//    
//    new MockUp<JerseyClientBuilder>(){
//    	
//    	@Mock
//        public void $init(Environment environment) {
//        } 
//    	
//    	@Mock
//        public JerseyClientBuilder using(JerseyClientConfiguration configuration) {
//    		return clientBuilder;
//        }
//    	
//    	@Mock
//        public JerseyClientBuilder using(Environment environment) {
//			return clientBuilder;        
//        }
//    	
//    	@Mock
//        public Client build(String name) {
//			return client;
//        }
//    };
//    
//    new MockUp<NashornController>(){
//    	@Mock
//        public void $init(NashornScriptEngine engine) {
//    		
//        }  
//    };
//
//    Main app = new Main();
//    ApplicationConfiguration configuration = new ApplicationConfiguration();
//    app.run(configuration, environment);
//  }
  
  @Test
  public void initialize_bundleAdded_IfInitialised(@Mocked Bootstrap<ApplicationConfiguration> configuration){	 
	  AssetsBundle expectedBundle = new AssetsBundle("/test", "/");
	  new MockUp<Bootstrap<ApplicationConfiguration>>(){		 		 
		  
		  @Mock
		  public void addBundle(Bundle bundle){
		  }
	  };
	  
	  // test if the addBundle method is called in the initialize function
	  new Expectations(){{
		 configuration.addBundle(expectedBundle);		 
	  }};
	  
	  Main app = new Main();
	  app.initialize(configuration);
  }
}
