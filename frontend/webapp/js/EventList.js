import React from 'react';
import axios from 'axios';
import Event from '../js/Event'
import Form from '../js/Form';

class EventList extends React.Component {

	constructor(props) {
  	super(props);
  	this.state = {
			events : this.props.events,
			pollInterval: this.props.pollInterval,
			eventsEndpointUrl: '/events'
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
		this.loadEventsFromServer(this, this.state.eventsEndpointUrl);
		setInterval(this.loadEventsFromServer.bind(null, this, this.state.eventsEndpointUrl), this.state.pollInterval);
	}

	render(){
		const eventsEndpointUrl = this.state.eventsEndpointUrl;
		const events = this.state.events.map(function(event) {
			return <Event data={event} key={event.id} url={eventsEndpointUrl}/>
		});
		return (
			<div>
                <h2>Events</h2>
                <Form/>
				<ul>
					{events}
				</ul>
			</div>
		)
	}
}

export default EventList;
