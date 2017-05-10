import React from 'react';
import axios from 'axios';

var Events = React.createClass({
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
		this.eventsUrl = 'http://localhost:8080/events';
		this.loadEventsFromServer(this, this.eventsUrl);
		setInterval(this.loadEventsFromServer.bind(null, this, this.eventsUrl),
				this.props.pollInterval);
	},

	render : function() {
		return React.createElement("ul", null, this.state.events.map(function(
				event, index) {
			return React.createElement("li", {
				key : index
			}, event.name);
		}));
	}
});

export default Events;