import React from 'react';
import axios from 'axios';

class Form extends React.Component {
	constructor(props) {
  	super(props);
  	this.state = {
			eventName: '',
			eventLocation: '',
			eventDescription: '',
			eventDate: '',
			eventsEndpointUrl: '/events'
		};

		// allow access to 'this' from within defined functions
		this.onSubmit = this.onSubmit.bind(this)
		this.onNameChange = this.onNameChange.bind(this)
		this.onLocationChange = this.onLocationChange.bind(this)
		this.onDescriptionChange = this.onDescriptionChange.bind(this)
		this.onDateChange = this.onDateChange.bind(this)
	}

	onSubmit(e){
		e.preventDefault();
		const eventsEndpointUrl = this.state.eventsEndpointUrl
		axios.post(eventsEndpointUrl,
				{
					name: this.state.eventName,
					location: this.state.eventLocation,
					description: this.state.eventDescription,
					date: this.state.eventDate
				});

		// reset the values in the form
		this.setState({
		      eventName: '',
		      eventLocation: '',
		      eventDescription: '',
		      eventDate: ''
				});
	}

	onNameChange(e){
		this.setState({eventName: e.target.value})
	}

	onLocationChange(e){
		this.setState({eventLocation: e.target.value})
	}

	onDescriptionChange(e){
		this.setState({eventDescription: e.target.value})
	}

	onDateChange(e){
		this.setState({eventDate: e.target.value});
	}

	render(){
		return (
			<form onSubmit={this.onSubmit}>
                <div className="form-group">
                    <label for="event-name">Event Name</label>
				    <input id="event-name" className="form-control" type='text' placeholder='Event Name' required='true' value={this.state.eventName} onChange={this.onNameChange} />
                </div>
                <div className="form-group">
                    <label for="description">Description</label>
				    <textarea id="description" className="form-control" placeholder='Description' required='false' value={this.state.eventDescription} onChange={this.onDescriptionChange} />
                </div>
                <div className="form-group">
                    <label for="location">Location</label>
				    <input id="location" className="form-control" type='text' placeholder='Location' required='true' value={this.state.eventLocation} onChange={this.onLocationChange} />
                </div>
                <div className="form-group">
                    <label for="date">Date</label>
				    <input id="date" className="form-control" type='date' placeholder='Date' required='true' value={this.state.eventDate} onChange={this.onDateChange} />
                </div>
                <button className="btn btn-primary" type='submit'>Create Event</button>
			</form>
		)
	}
}

export default Form;
