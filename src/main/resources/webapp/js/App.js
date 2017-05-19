import React from 'react';
import Form from '../js/Form';
import EventsList from '../js/EventsList';

class App extends React.Component {
	constructor(props) {
  	super(props);
  	this.state = {
			data : props.data,
			webApiGatewayUrl: props.url,
			pollInterval: props.pollInterval
		};
	}

	render() {
		return (
			<div>
			<Form url={this.state.webApiGatewayUrl} />
			<EventsList events={this.state.data} pollInterval={this.state.pollInterval} url={this.state.webApiGatewayUrl}/>
			</div>
		)
	}
}

export default App;
