import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import CreateEventForm from '../js/CreateEventForm';
import EventsList from '../js/EventsList';

const eventsUrl = 'https://dropwizardheroku-webgateway.herokuapp.com/events';

// Server Side
//
// Render the React components on the server.
global.renderServerCreateEventForm = function () {
  return ReactDOMServer.renderToString(		  
		  buildCreateEventFormComponent()
	    );
};

global.renderServerEventsList = function (eventsListData) {
  var eventsList = Java.from(eventsListData);  
  return ReactDOMServer.renderToString(		  
		  buildEventsListComponent(eventsList)
	    );
};

// Build the React components.
function buildCreateEventFormComponent(){
	return React.createElement(CreateEventForm, {url: eventsUrl});
}

function buildEventsListComponent(eventsList){
	return React.createElement(EventsList, {events: eventsList, pollInterval: 2000, url: eventsUrl});
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
	ReactDOM.render(
			buildCreateEventFormComponent(),
			document.getElementById("createEventForm")
    );
	
    var eventsList = eventsListData || [];
    ReactDOM.render(
    		buildEventsListComponent(eventsList),
    		document.getElementById("eventsList")
    );
};