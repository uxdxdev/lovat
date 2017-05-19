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
			<li className='EventsList-item' key={this.state.index}>
				<h2 className='EventsListItem-name'>{this.state.data.name}</h2>
				<div>{this.state.data.description}</div>
				<div>{this.state.data.location}</div>
				<div>{this.state.data.date}</div>
				<Buttons/>
			</li>
		)
	}
})

var EventsList = React.createClass({
	propTypes: {
    events: React.PropTypes.array.isRequired
  },
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
		const events = this.state.events.map(function(event, index) {
			return <Event data={event} index={index}/>
		});
		return (
			<div className='EventsList'>
				<h2>Events List</h2>
				<ul className='EventsList-list'>
					{events}
				</ul>
			</div>
		)
	}
})

export default EventsList;
