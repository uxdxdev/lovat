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
					<Form/>
					<EventList events={this.state.eventsData} pollInterval={this.state.eventsPollInterval}/>
				</div>
				<div className="Service">
					<TwitterApi tweets={this.state.tweetsData} pollInterval={this.state.tweetsPollInterval}/>
				</div>
				<div className="Service">
					<WebsocketChat />
				</div>
				<div className="Service">
					<CryptoCurrencies data={this.state.pairsData} pollInterval={this.state.pairsPollInterval}/>
				</div>
			</div>
		)
	}
}

export default App;
