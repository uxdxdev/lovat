import React from 'react';
import axios from 'axios';

class Event extends React.Component {
	constructor(props){
  	super(props);
  	this.state = {
			data : props.data,
			key: props.key,
			url: props.url
		};

		this.edit = this.edit.bind(this)
		this.remove = this.remove.bind(this)
	}

	edit(){
	}

	remove(){
		const requestUrlWithParam = this.state.url + '/' + this.state.data.id;
		axios.delete(requestUrlWithParam).then(function(res) {
		});
	}

	render(){
		return (
			<li className='EventsList-item' key={this.state.data.id}>
				<h2 className='EventsListItem-name'>{this.state.data.name}</h2>
				<div>{this.state.data.description}</div>
				<div>{this.state.data.location}</div>
				<div>{this.state.data.date}</div>
				<span>
					<button onClick={this.edit}>EDIT</button>
					<button onClick={this.remove}>X</button>
				</span>
			</li>
		)
	}
}

class EventsList extends React.Component {
	propTypes: {
    events: React.PropTypes.array.isRequired
  }

	constructor(props) {
  	super(props);
  	this.state = {
			events : this.props.events,
			url: this.props.url,
			pollInterval: this.props.pollInterval
		};

		this.loadEventsFromServer = this.loadEventsFromServer.bind(this)
	}

	loadEventsFromServer(component, eventsUrl) {
		axios.get(eventsUrl).then(function(res) {
			var eventList = res.data.list;
			component.setState({
				events : eventList
			});
		});
	}

	componentDidMount() {
		this.loadEventsFromServer(this, this.state.url);
		setInterval(this.loadEventsFromServer.bind(null, this, this.state.url), this.state.pollInterval);
	}

	render(){
		const eventsUrlEndpoint = this.state.url;
		const events = this.state.events.map(function(event) {
			return <Event data={event} key={event.id} url={eventsUrlEndpoint}/>
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
}

export default EventsList;
