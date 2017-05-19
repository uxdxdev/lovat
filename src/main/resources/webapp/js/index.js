import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import App from '../js/App';

//const eventsUrl = 'https://dropwizardheroku-webgateway.herokuapp.com/events';
const eventsUrl = 'http://localhost:8080/events';

global.renderServer = function (eventsListData) {
  var eventsList = Java.from(eventsListData);
  return ReactDOMServer.renderToString(buildApplication(eventsList));
};

function buildApplication(eventsList){
	return React.createElement(App, {events: eventsList, pollInterval: 2000, url: eventsUrl});
}

// Client Side
global.init = function () {

	// Called from index, request the json data for events from the gateway
	// and attach the React component event handlers.
	axios.get(eventsUrl).then(function(res) {
		var data = res.data.list;
		renderClient(data);
	});
};

// Attach the React event handlers to the client application.
global.renderClient = function (eventsListData) {
    var eventsList = eventsListData || [];
    ReactDOM.render(
    		buildApplication(eventsList),
    		document.getElementById("react-root")
    );
};
