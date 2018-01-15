# Dropwizard web API gateway using React server side rendering

[![Build Status](https://travis-ci.org/damorton/lovat.svg?branch=master)](https://travis-ci.org/damorton/lovat) [![Coverage Status](https://coveralls.io/repos/github/damorton/lovat/badge.svg?branch=master)](https://coveralls.io/github/damorton/lovat?branch=master) ![Health Check](https://healthchecks.io/badge/026dd894-eece-42d5-9063-14c343/AV6vSwuv/taupe.svg)

## Event Microservice (Build and Run First)
https://dropwizardheroku-event-service.herokuapp.com/

## Build

- Heroku config

```
heroku config:set WEBGATEWAY_OPTS='-Ddw.server.connector.port=$PORT'
heroku config:set DATABASE_URL='postgres://<database-url>'
```

- Clone repo
```
git clone https://github.com/damorton/lovat.git
```
- Build
```
cd lovat
./gradlew stage
```
- Run 
```
heroku local
```