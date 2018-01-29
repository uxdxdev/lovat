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
			<div className="wrapper">
				<Header/>
                <section className="container main">
                    <div className="row">
                        <div className="col-lg-3 left-side-bar">
                            <CryptoCurrencies data={this.state.pairsData} pollInterval={this.state.pairsPollInterval}/>
                            <EventList events={this.state.eventsData} pollInterval={this.state.eventsPollInterval}/>
                        </div>
                        <div className="col-lg-6 dashboard">
                            <TwitterApi tweets={this.state.tweetsData} pollInterval={this.state.tweetsPollInterval}/>
                        </div>
                        <div className="col-lg-3 right-side-bar">
                            <WebsocketChat />
                        </div>
                    </div>
                </section>
                <Footer/>
			</div>
		)
	}
}

export default App;
