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
			url: props.url
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
		axios.post(this.state.url,
				{
					name: this.state.eventName,
					location: this.state.eventLocation,
					description: this.state.eventDescription,
					date: this.state.eventDate
				})
				.then(function(response){
					document.getElementById("notification-bar").innerHTML = 'Create event successful';
					// refresh the events list
				})
				.catch(function (error) {
					document.getElementById("notification-bar").innerHTML = 'Error creating event';
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
			<div className='CreateEventForm'>
			<h2>Create Event</h2>
			<form onSubmit={this.onSubmit}>
				<input type='text' placeholder='Event Name (required)' required='true' value={this.state.eventName} onChange={this.onNameChange} />
				<textarea placeholder='Description' required='true' value={this.state.eventDescription} onChange={this.onDescriptionChange} />
				<input type='text' placeholder='Location (required)' required='true' value={this.state.eventLocation} onChange={this.onLocationChange} />
				<input type='date' placeholder='Date (required)' required='true' value={this.state.eventDate} onChange={this.onDateChange} />
				<button type='submit'>Create Event</button>
				<div id='notification-bar'>Notifications</div>
				</form>
			</div>
		)
	}
}

export default Form;
