import React from 'react';
import axios from 'axios';

var Buttons = React.createClass({
	edit() {
		alert('Editing event');
	},
	remove(){
		alert('Removing event');
	},
	render(){
		return (
			<span>
				<button onClick={this.edit}>EDIT</button>
				<button onClick={this.remove}>X</button>
			</span>
		)
	}
})

var Event = React.createClass({
	getInitialState() {
		return {
			data : this.props.data,
			index: this.props.index
		};
	},
	render(){
		return (
			React.createElement('li', {className: 'EventsList-item', key : this.state.index},
				React.createElement('h2', {className: 'EventsListItem-name'}, this.state.data.name),
				React.createElement('div', null, this.state.data.description),
				React.createElement('div', null, this.state.data.location),
				React.createElement('div', null, this.state.data.date),
				<Buttons/>
			)
		)
	}
})

var EventsList = React.createClass({
	getInitialState() {
		return {
			events : this.props.events,
			url: this.props.url,
			pollInterval: this.props.pollInterval
		};
	},

	loadEventsFromServer(component, eventsUrl) {
		axios.get(eventsUrl).then(function(res) {
			var eventList = res.data.list;
			component.setState({
				events : eventList
			});
		});
	},

	componentDidMount() {
		this.loadEventsFromServer(this, this.state.url);
		setInterval(this.loadEventsFromServer.bind(null, this, this.state.url), this.state.pollInterval);
	},
	render(){
		return (
			React.createElement('div', {className: 'EventsList'},
					React.createElement('h2', null, 'Events List'),
					React.createElement('ul', {className: 'EventsList-list'}, this.state.events.map(function(event, index) {
						return <Event data={event} index={index}/>
					})
					)
			)
		)
	}
})

export default EventsList;
