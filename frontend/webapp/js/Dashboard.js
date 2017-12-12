import React from 'react';
import Navbar from '../js/Navbar'
import Form from '../js/Form';
import EventList from '../js/EventList';
import TwitterApi from '../js/TwitterApi';
import CryptoCurrencies from '../js/CryptoCurrencies';
import WebsocketChat from '../js/WebsocketChat';

class App extends React.Component {
	constructor(props) {
  	super(props);
  	this.state = {
			eventsData : props.eventsData,
			tweetsData : props.tweetsData,
			pairsData : props.pairsData,
			webApiGatewayUrl: props.url,
			eventsPollInterval: props.eventsPollInterval,
			tweetsPollInterval: props.tweetsPollInterval,
			pairsPollInterval: props.pairsPollInterval
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
					<WebsocketChat />
				</div>
				<div className="Service">
					<CryptoCurrencies data={this.state.pairsData} pollInterval={this.state.pairsPollInterval} url={this.state.webApiGatewayUrl}/>
				</div>
			</div>
		)
	}
}

export default App;
