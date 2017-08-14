import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import App from '../js/App';
import Login from '../js/Login';

const webApiGatewayUrl = 'https://lovat.herokuapp.com';
//const webApiGatewayUrl = 'http://localhost:8080';

// Server side

// Login
global.renderServerLogin = function () {
  return ReactDOMServer.renderToString(buildLogin());
};

// Dashboard
global.renderServerDashboard = function (propsFromServer) {
  var props = Java.from(propsFromServer);
  return ReactDOMServer.renderToString(buildDashboard(props));
};

function buildLogin(){
	return React.createElement(Login, {url: webApiGatewayUrl});
}

function buildDashboard(props){
	return React.createElement(App, {data: props, pollInterval: 2000, url: webApiGatewayUrl});
}

// Client Side
if(typeof window !== "undefined"){
  window.onload = function () {
    // Login
    if(document.getElementById('react-root-login')) {
      renderClientLogin();
    }

    // Dashboard
    if(document.getElementById('react-root-dashboard')) {
      const eventsEndpointUrl = webApiGatewayUrl + '/events'
    	axios.get(eventsEndpointUrl).then(function(res) {
    		var events = res.data.list;
    		renderClientDashbaord(events);
    	});
    }
  }
}

// Attach the React event handlers to the client application.
global.renderClientLogin = function () {
    ReactDOM.render(
    		buildLogin(),
    		document.getElementById("react-root-login")
    );
};

global.renderClientDashbaord = function (propsFromClient) {
    var props = propsFromClient || [];
    ReactDOM.render(
    		buildDashboard(props),
    		document.getElementById("react-root-dashboard")
    );
};
