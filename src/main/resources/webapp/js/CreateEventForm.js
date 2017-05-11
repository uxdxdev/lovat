import React from 'react';

var CreateEventForm = React.createClass({
	onSubmit: function(e){
		e.preventDefault();
		console.log("form submit");
	},
	render : function() {
		return React.createElement('div', {className: 'CreateEventForm'}, 
					React.createElement('h2', null, 'Create Event'),
					React.createElement('form', {onSubmit: this.onSubmit},
					        React.createElement('input', {
					          type: 'text',
					          placeholder: 'Name (required)',
					        }),
					        React.createElement('input', {
					          type: 'email',
					          placeholder: 'Email',
					        }),
					        React.createElement('textarea', {
					          placeholder: 'Description',
					        }),
					        React.createElement('button', {type: 'submit'}, "Create Event")
					      )
		);
	}
});

export default CreateEventForm;