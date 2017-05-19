import React from 'react';
import axios from 'axios';
import Event from '../js/Event'

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
