import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import Dashboard from '../js/Dashboard';
import Login from '../js/Login';
import Index from '../js/Index';

const webApiGatewayUrl = 'https://lovat.herokuapp.com';
//const webApiGatewayUrl = 'http://localhost:8080';

// Server side

global.renderServerIndex = function () {
  return ReactDOMServer.renderToString(buildIndex());
};

// Login
global.renderServerLogin = function () {
  return ReactDOMServer.renderToString(buildLogin());
};

// Dashboard
global.renderServerDashboard = function (eventsDataFromServer, tweetsDataFromServer, pairsDataFromServer) {
  var eventsData = Java.from(eventsDataFromServer);
  var tweetsData = Java.from(tweetsDataFromServer);
  var assetPairsData = Java.from(pairsDataFromServer);
  return ReactDOMServer.renderToString(buildDashboard(eventsData, tweetsData, assetPairsData));
};

function buildIndex(){
	return React.createElement(Index, {url: webApiGatewayUrl});
}

function buildLogin(){
	return React.createElement(Login, {url: webApiGatewayUrl});
}

function buildDashboard(eventsDataFromServer, tweetsDataFromServer, pairsDataFromServer){
	return React.createElement(Dashboard, {eventsData: eventsDataFromServer, tweetsData: tweetsDataFromServer, pairsData: pairsDataFromServer, eventsPollInterval: 2000, tweetsPollInterval: 5000, pairsPollInterval: 10000, url: webApiGatewayUrl});
}

// Client Side
if(typeof window !== "undefined"){
	
  window.onload = function () {

    // Index
    if(document.getElementById('react-root-index')) {
      renderClientIndex();
    }

    // Login
    if(document.getElementById('react-root-login')) {
      renderClientLogin();
    }

    // Dashboard
    if(document.getElementById('react-root-dashboard')) {
      const eventsEndpointUrl = webApiGatewayUrl + '/events';
      const tweetsEndpointUrl = webApiGatewayUrl + '/tweets';
      // TODO take pair parameter from client
      const pairsEndpointUrl = webApiGatewayUrl + '/kraken';

      var events;
      var tweets;
      var pairData;

      axios.all([
        axios.get(eventsEndpointUrl).then(function(res) {
      		events = res.data.list;
      	}),
        axios.get(tweetsEndpointUrl).then(function(res) {
          tweets = res.data.list;
        }),
        axios.get(pairsEndpointUrl).then(function(res) {
          pairData = res.data.list;
        })

      ]).catch(error => console.log(error));

      // render Dashboard
      renderClientDashbaord(events, tweets, pairData);
    }
  }
}

// Attach the React event handlers to the client application.
global.renderClientIndex = function () {
    ReactDOM.render(
    		buildIndex(),
    		document.getElementById("react-root-index")
    );
};

global.renderClientLogin = function () {
    ReactDOM.render(
    		buildLogin(),
    		document.getElementById("react-root-login")
    );
};

global.renderClientDashbaord = function (eventPropsFromClient, tweetPropsFromClient, pairDataFromClient) {
    var eventsData = eventPropsFromClient || [];
    var tweetsData = tweetPropsFromClient || [];
    var pairsData = pairDataFromClient || [];
    ReactDOM.render(
    		buildDashboard(eventsData, tweetsData, pairsData),
    		document.getElementById("react-root-dashboard")
    );
};

// websocket ui
global.connect = function() {
	createNewSocketConnection();
};

global.close = function(){
	closeSocketConnection();
};

global.send = function(){
	sendMessageOverSocket();
};