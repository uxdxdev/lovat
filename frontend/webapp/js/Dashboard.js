import React from 'react';
import Header from './Header'
import EventList from '../js/EventList';
import TwitterApi from '../js/TwitterApi';
import CryptoCurrencies from '../js/CryptoCurrencies';
import WebsocketChat from '../js/WebsocketChat';
import Footer from "../js/Footer";

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
				<Header/>
                <div className="container-fluid">
                    <div className="row">
                        <div className="col">
                            <EventList events={this.state.eventsData} pollInterval={this.state.eventsPollInterval}/>
                        </div>
                        <div className="col">
                            <TwitterApi tweets={this.state.tweetsData} pollInterval={this.state.tweetsPollInterval}/>
                        </div>
                        <div className="col">
                            <WebsocketChat />
                        </div>
                        <div className="col">
                            <CryptoCurrencies data={this.state.pairsData} pollInterval={this.state.pairsPollInterval}/>
                        </div>
                    </div>
                </div>
                <Footer/>
			</div>
		)
	}
}

export default App;
