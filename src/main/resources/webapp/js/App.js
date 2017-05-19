import React from 'react';
import Form from '../js/Form';
import EventsList from '../js/EventsList';

class App extends React.Component {
	constructor(props) {
  	super(props);
  	this.state = {
			events : props.events,
			url: props.url,
			pollInterval: props.pollInterval
		};
	}

	render() {
		return (
			<div>
			<Form url={this.state.url} />
			<EventsList events={this.state.events} pollInterval={this.state.pollInterval} url={this.state.url}/>
			</div>
		)
	}
}

export default App;
