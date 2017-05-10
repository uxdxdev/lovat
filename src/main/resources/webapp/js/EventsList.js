import React from 'react';
import axios from 'axios';

var EventsList = React.createClass({
	getInitialState : function() {
		return {
			events : this.props.events
		};
	},

	loadEventsFromServer : function(component, eventsUrl) {
		axios.get(eventsUrl).then(function(res) {
			var eventList = res.data.list;
			component.setState({
				events : eventList
			});
		});
	},

	componentDidMount : function() {
		const eventsUrl = 'http://localhost:8080/events';
		this.loadEventsFromServer(this, eventsUrl);
		setInterval(this.loadEventsFromServer.bind(null, this, eventsUrl),
				this.props.pollInterval);
	},

	render : function() {
		return React.createElement('div', {id:'eventsList'}, 
					React.createElement('h2', null, 'Events List'), 
					React.createElement("ul", null, this.state.events.map(function(event, index) {
						return React.createElement("li", {key : index}, event.name);
					}))
				);				
	}
});

export default EventsList;