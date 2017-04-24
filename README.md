# Dropwizard Heroku Event Microservice

[![Build Status](https://travis-ci.org/damorton/dropwizardheroku-event-service.svg?branch=master)](https://travis-ci.org/damorton/dropwizardheroku-event-service) [![Coverage Status](https://coveralls.io/repos/github/damorton/dropwizardheroku-event-service/badge.svg?branch=master)](https://coveralls.io/github/damorton/dropwizardheroku-event-service?branch=master)

https://dropwizardheroku-event-service.herokuapp.com//

A Dropwizard Heroku Event Microservice deployed to Heroku with a hosted PostgreSQL database.

## API Endpoints for ExampleResource

```
curl https://dropwizardheroku-event-service.herokuapp.com//v1/api/example/query?message=hello
curl https://dropwizardheroku-event-service.herokuapp.com//v1/api/example/hello
curl -X POST -d 'message=hello' https://dropwizardheroku-event-service.herokuapp.com//v1/api/example/postparam
curl -X POST -d 'hello' https://dropwizardheroku-event-service.herokuapp.com//v1/api/example/postbody
```

## API Endpoints for EventResource

```
curl https://dropwizardheroku-event-service.herokuapp.com//v1/api/events
curl https://dropwizardheroku-event-service.herokuapp.com//v1/api/events/{name}
curl -H "Content-Type: application/json" -X POST -d '{"name":"Sesh","description":"Drinkys","location":"Toms","date":"2017-04-11"}' https://dropwizardheroku-event-service.herokuapp.com//v1/api/events
```

## Project Structure

### Example package names:

```
com.bitbosh.DropwizardHeroku.api (Resources & Representations. Communicates will all parts of the Microservice and contains Domain objects)
com.bitbosh.DropwizardHeroku.service (Coordination across multiple Domain modules, multiple business transactions)
com.bitbosh.DropwizardHeroku.domain (Domain Modules/Classes, this Microservices business logic)
com.bitbosh.DropwizardHeroku.repositories (Dao, handles multiple Domain entities and has access to persistent storage)
com.bitbosh.DropwizardHeroku.gateways (Encapsulates message passing and error handling with other Microservices)
com.bitbosh.DropwizardHeroku.client (HTTP Client to communicate with other Microservices)
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
    │   │       └── DropwizardHeroku    
    │   │           ├── api
    │   │           ├── service
    │   │           ├── domain
    │   │           ├── repositories
    │   │           ├── gateways
    │   │           ├── client
    │   │           ├── DropwizardHerokuApplication.java
    │   │           ├── DropwizardHerokuConfiguration.java
    │   │           ├── ...
    │   └── resources
    │       ├── assets
    │       └── banner.txt
    └── test
        ├── java
        │   └── com
        │       └── DropwizardHeroku
        │           ├── api
        │           ├── service
        │           ├── domain
        │           ├── repositories
        │           ├── gateways
        │           ├── client
        │           ├── ...
        └── resources
            └── fixtures
```

# Tutorial

http://www.bitbosh.com/2017/04/microservices-with-dropwizard-on-heroku.html
