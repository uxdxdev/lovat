import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';

var Events = React.createClass({
	getInitialState: function () {
		return {events: this.props.events};
	},

  loadEventsFromServer: function(component, eventsUrl) {	  
	  axios.get(eventsUrl).then(function (res) {
		  var eventList = res.data.list;		  
		  component.setState({ events: eventList });
	  });
  },

  componentDidMount: function() {
    this.eventsUrl = 'http://localhost:8080/events';
    this.loadEventsFromServer(this, this.eventsUrl);
    setInterval(this.loadEventsFromServer.bind(null, this, this.eventsUrl), this.props.pollInterval);
  },

  render: function() {
	  return React.createElement(
		          "ul",
		          null,
		          this.state.events.map(function (event, index) {
		            return React.createElement(
		              "li",
		              { key: index },
		              event.name
		            );
		          })
		        );
	  }
});

// Server Side
//
// Render the React components on the server.
global.renderServerEvents = function (eventList) {
  var data = Java.from(eventList);  
  return ReactDOMServer.renderToString(
	        React.createElement(Events, {events: data, pollInterval: 5000})
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
        React.createElement(Events, {events: data, pollInterval: 5000}),
        document.getElementById("events")
    );
};