import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import EventsList from '../js/EventsList';

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

// Client Side
//
// Called from index, request the json data for events from the gateway
// and attach the React component event handlers.
global.init = function (eventsUrl) {
	axios.get(eventsUrl).then(function(res) {
		var data = res.data.list;		
		renderClient(data);
	});
};	

// Attach the React event handlers on the client.
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

// Build the React components
function buildCreateEventFormComponent(){
	return React.createElement('div', null, 'Create Events Form');
}

function buildEventsListComponent(eventsList){
	return React.createElement(EventsList, {events: eventsList, pollInterval: 5000});
}