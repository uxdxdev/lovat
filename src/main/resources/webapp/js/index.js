import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import App from '../js/App';

const webApiGatewayUrl = 'https://dropwizardheroku-webgateway.herokuapp.com';
//const webApiGatewayUrl = 'http://192.168.1.5:8080';

global.renderServer = function (propsFromServer) {
  var props = Java.from(propsFromServer);
  return ReactDOMServer.renderToString(buildApplication(props));
};

function buildApplication(props){
	return React.createElement(App, {data: props, pollInterval: 2000, url: webApiGatewayUrl});
}

// Client Side
global.init = function () {

	// Called from index, request the json data for events from the gateway
	// and attach the React component event handlers.
  const eventsEndpointUrl = webApiGatewayUrl + '/events'
	axios.get(eventsEndpointUrl).then(function(res) {
		var events = res.data.list;
		renderClient(events);
	});
};

// Attach the React event handlers to the client application.
global.renderClient = function (propsFromClient) {
    var props = propsFromClient || [];
    ReactDOM.render(
    		buildApplication(props),
    		document.getElementById("react-root")
    );
};
