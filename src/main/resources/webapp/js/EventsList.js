import React from 'react';
import axios from 'axios';

var EventsList = React.createClass({
	getInitialState : function() {
		return {
			events : this.props.events,
			url: this.props.url
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
		const eventsUrl = this.state.url;
		this.loadEventsFromServer(this, eventsUrl);
		setInterval(this.loadEventsFromServer.bind(null, this, eventsUrl),
				this.props.pollInterval);
	},

	render : function() {
		return React.createElement('div', {className: 'EventsList'}, 
					React.createElement('h2', null, 'Events List'), 
					React.createElement('ul', {className: 'EventsList-list'}, this.state.events.map(function(event, index) {
						return React.createElement('li', {className: 'EventsList-item', key : index},
								React.createElement('h2', {className: 'EventsListItem-name'}, event.name),
								React.createElement('div', null, event.description),
								React.createElement('div', null, event.location),
								React.createElement('div', null, event.date)
								)
					}))
				);				
	}
});

export default EventsList;