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
				<div className="Service">	
					<h1>Events</h1>
					<Form url={this.state.webApiGatewayUrl} />
					<EventList events={this.state.data} pollInterval={this.state.pollInterval} url={this.state.webApiGatewayUrl}/>
				</div>
				<div className="Service">
					<h1>Latex2Pdf</h1>
					<p>Latex2Pdf Service description.</p>
				</div>
				<div className="Service">
					<h1>Service#3</h1>
					<p>Service#3 description.</p>
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
