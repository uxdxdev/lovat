import React from 'react';
import Navbar from '../js/Navbar'
import Form from '../js/Form';
import EventList from '../js/EventList';
import TwitterApi from '../js/TwitterApi';

class App extends React.Component {
	constructor(props) {
  	super(props);
  	this.state = {
			eventsData : props.eventsData,
			tweetsData : props.tweetsData,
			webApiGatewayUrl: props.url,
			eventsPollInterval: props.eventsPollInterval,
			tweetsPollInterval: props.tweetsPollInterval
		};
	}

	render() {
		return (
			<div>
				<Navbar/>
				<div className="Service">
					<h1>Events</h1>
					<Form url={this.state.webApiGatewayUrl} />
					<EventList events={this.state.eventsData} pollInterval={this.state.eventsPollInterval} url={this.state.webApiGatewayUrl}/>
				</div>
				<div className="Service">
					<TwitterApi tweets={this.state.tweetsData} pollInterval={this.state.tweetsPollInterval} url={this.state.webApiGatewayUrl}/>
				</div>
				<div className="Service">
					<h1>Latex2Pdf</h1>
					<p>Latex2Pdf Service description.</p>
				</div>
				<div className="Service">
					<h1>Service#4</h1>
					<p>Service#4 description.</p>
				</div>
			</div>
		)
	}
}

export default App;
