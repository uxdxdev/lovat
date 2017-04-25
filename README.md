# Dropwizard Heroku Web API Gateway Microservice

[![Build Status](https://travis-ci.org/damorton/dropwizardheroku-webgateway.svg?branch=master)](https://travis-ci.org/damorton/dropwizardheroku-webgateway) [![Coverage Status](https://coveralls.io/repos/github/damorton/dropwizardheroku-webgateway/badge.svg?branch=master)](https://coveralls.io/github/damorton/dropwizardheroku-webgateway?branch=master)

https://dropwizardheroku-webgateway.herokuapp.com//

A Dropwizard Heroku Web API Gateway Microservice deployed to Heroku.

## API Endpoints for Web API Gateway

```
curl https://dropwizardheroku-webgateway.herokuapp.com/
```

## Project Structure

### Example package names:

```
com.bitbosh.dropwizardheroku.webgateway.api (Resources & Representations. Communicates will all parts of the Microservice and contains Domain objects)
com.bitbosh.dropwizardheroku.webgateway.service (Coordination across multiple Domain modules, multiple business transactions)
com.bitbosh.dropwizardheroku.webgateway.domain (Domain Modules/Classes, this Microservices business logic)
com.bitbosh.dropwizardheroku.webgateway.repositories (Dao, handles multiple Domain entities and has access to persistent storage)
com.bitbosh.dropwizardheroku.webgateway.gateways (Encapsulates message passing and error handling with other Microservices)
com.bitbosh.dropwizardheroku.webgateway.client (HTTP Client to communicate with other Microservices)
...
```

### Structure

```
.
├── config.yml
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── dropwizardheroku
	│   │	       	└── webgateway    
    │   │          		├── api
    │   │           	├── service
    │   │           	├── domain
    │   │           	├── repositories
    │   │           	├── gateways
    │   │           	├── client
    │   │           	├── Main.java
    │   │           	├── ApplicationConfiguration.java
    │   │           	├── ...
    │   └── resources
    │       ├── assets
    │       	└── index.html
    └── test
        ├── java
        │   └── com
        │       └── dropwizardheroku
        │       	└── webgateway
        │	           	├── api
        │   	       	├── service
        │       	   	├── domain
        │         		├── repositories
        │	           	├── gateways
        │   	        ├── client
        │       	    ├── MainUnitTest.java
        │           	├── ApplicationConfigurationUnitTest.java
        │           	├── ...
        └── resources
            └── fixtures
```

# Tutorial

http://www.bitbosh.com/2017/04/microservices-with-dropwizard-on-heroku.html
