import React from 'react';
import axios from 'axios';

var CreateEventForm = React.createClass({	
	getInitialState : function() {
		return {
			eventName: '',
		    eventLocation: '',
		    eventDescription: '',
		    eventDate: '',
		    url: this.props.url
		};
	},
	onSubmit: function(e){
		e.preventDefault();		
		axios.post(this.state.url,
				{ 
					name: this.state.eventName, 
					location: this.state.eventLocation, 
					description: this.state.eventDescription, 
					date: this.state.eventDate 
				})
				.then(function(response){
				}); 
		this.setState({
		      eventName: '',
		      eventLocation: '',
		      eventDescription: '',
		      eventDate: ''
		    });
	},
	onNameChange: function(e){
		this.setState({eventName:e.target.value});
	},
	onDescriptionChange: function(e){
		this.setState({eventDescription:e.target.value});
	},
	onLocationChange: function(e){
		this.setState({eventLocation:e.target.value});
	},
	onDateChange: function(e){
		this.setState({eventDate:e.target.value});
	},
	render : function() {
		return React.createElement('div', {className: 'CreateEventForm'}, 
					React.createElement('h2', null, 'Create Event'),
					React.createElement('form', {onSubmit: this.onSubmit},
					        React.createElement('input', {
					        	type: 'text',
					        	placeholder: 'Event Name (required)',
							    required: 'true',
							    value: this.state.eventName,
							    onChange: this.onNameChange
					        }),
					        React.createElement('textarea', {					          
					        	placeholder: 'Description',
					        	value: this.state.eventDescription,
					        	onChange: this.onDescriptionChange
					        }),
					        React.createElement('input', {
					        	type: 'text',
					        	placeholder: 'Location (required)',
							    required: 'true',
							    value: this.state.eventLocation,
							    onChange: this.onLocationChange
					        }),
					        React.createElement('input', {
							    type: 'date',
							    placeholder: 'Date (required)',
							    required: 'true',
							    value: this.state.eventDate,
							    onChange: this.onDateChange
						    }),
					        React.createElement('button', {type: 'submit'}, "Create Event")
					      )
		);
	}
});

export default CreateEventForm;