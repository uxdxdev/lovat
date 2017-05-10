import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import Events from '../js/Events';

// Server Side
//
// Render the React components on the server.
global.renderServerEvents = function (eventList) {
  var data = Java.from(eventList);  
  return ReactDOMServer.renderToString(
		  buildComponents(data)
	    );
};

// Client Side
//
// Called from index, request the json data for events from the gateway
// and attach the React component event handlers.
global.init = function (eventsUrl) {
	axios.get(eventsUrl).then(function(res) {
		var data = res.data.list;		
		renderClientEvents(data);
	});
};	

// Attach the React event handlers on the client.
global.renderClientEvents = function (eventList) {
    var data = eventList || [];
    ReactDOM.render(
    	buildComponents(data),
        document.getElementById("events")
    );
};

// Create the React components
function buildComponents(data){
	return React.createElement(Events, {events: data, pollInterval: 5000});
}