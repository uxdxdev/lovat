# Dropwizard Heroku Web API Gateway Microservice

[![Build Status](https://travis-ci.org/damorton/dropwizardheroku-webgateway.svg?branch=master)](https://travis-ci.org/damorton/dropwizardheroku-webgateway) [![Coverage Status](https://coveralls.io/repos/github/damorton/dropwizardheroku-webgateway/badge.svg?branch=master)](https://coveralls.io/github/damorton/dropwizardheroku-webgateway?branch=master)

## Event Microservice (Build and Run First)
https://dropwizardheroku-event-service.herokuapp.com/

## Browser client API 
https://dropwizardheroku-webgateway.herokuapp.com/

A Dropwizard Heroku Web API Gateway Microservice deployed to Heroku.

## Build

### (Important: Update the Events microservice local port number in index.jsx and client.js)

- Heroku config

```
heroku config:set DROPWIZARDHEROKU_WEBGATEWAY_OPTS='-Ddw.server.connector.port=$PORT'
heroku config:set DATABASE_URL='postgres://<database-url>'
```

- Clone repo
```
git clone https://github.com/damorton/dropwizardheroku-webgateway.git
```
- Build
```
cd dropwizardheroku-webgateway
./gradlew stage
```
- Run (Make sure the Events microservice is running https://github.com/damorton/dropwizardheroku-event-service)
```
heroku local
```

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
    │   │           		├── service
    │   │           		├── domain
    │   │           		├── repositories
    │   │           		├── gateways
    │   │           		├── client
    │   │           		├── Main.java
    │   │           		├── ApplicationConfiguration.java
    │   │           		├── ...
    │   └── resources
    │       ├── assets
    │       	└── index.html
    └── test
        ├── java
        │   └── com
        │       └── dropwizardheroku
        │       	└── webgateway
        │	           	├── api
        │   	       		├── service
        │       	   	├── domain
        │         		├── repositories
        │	           	├── gateways
        │   	        	├── client
        │       	    	├── MainUnitTest.java
        │           		├── ApplicationConfigurationUnitTest.java
        │           		├── ...
        └── resources
            └── fixtures
```

# Tutorial

http://www.bitbosh.com/2017/04/microservices-with-dropwizard-on-heroku.html
