import React from 'react';
import axios from 'axios';
import Event from '../js/Event'

class EventList extends React.Component {
	propTypes: {
    events: React.PropTypes.array.isRequired
  }

	constructor(props) {
  	super(props);
  	this.state = {
			events : this.props.events,
			pollInterval: this.props.pollInterval,
			webApiGatewayUrl: this.props.url,
			eventsEndpointUrl: props.url + '/events'
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
		const eventsEndpointUrl = this.state.eventsEndpointUrl
		const events = this.state.events.map(function(event) {
			return <Event data={event} key={event.id} url={eventsEndpointUrl}/>
		});
		return (
			<div className='EventList'>
				<h2>Events List</h2>
				<ul className='EventList-list'>
					{events}
				</ul>
			</div>
		)
	}
}

export default EventList;
