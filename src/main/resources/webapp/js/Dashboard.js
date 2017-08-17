import React from 'react';
import Navbar from '../js/Navbar'
import Form from '../js/Form';
import EventList from '../js/EventList';

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
				<Navbar/>
				<Form url={this.state.webApiGatewayUrl} />
				<EventList events={this.state.data} pollInterval={this.state.pollInterval} url={this.state.webApiGatewayUrl}/>
			</div>
		)
	}
}

export default App;
