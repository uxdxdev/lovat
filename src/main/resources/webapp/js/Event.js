import React from 'react';
import axios from 'axios';

var Buttons = React.createClass({
	getInitialState : function() {
		return {
			id : this.props.id,
			url: this.props.url
		};
	},
	edit(){
		alert('Editing event')
	},
	remove(){
		const requestUrlWithParam = this.state.url + '/' + this.state.id;
		alert('Delete event using url ' + requestUrlWithParam)
		axios.delete(requestUrlWithParam).then(function(res) {
			alert('Event deleted')
		});
	},
	render(){
		return React.createElement('span', null,
							React.createElement('button', {onClick: this.edit}, 'EDIT'),
							React.createElement('button', {onClick: this.remove}, 'X')
						);
	}
})

class Event extends React.Component {
	constructor(props){
  	super(props);
  	this.state = {
			data : props.data,
			key: props.key,
			url: props.url
		};
	}

	render(){
		return (
			<li className='EventsList-item' key={this.state.data.id}>
				<h2 className='EventsListItem-name'>{this.state.data.name}</h2>
				<div>{this.state.data.description}</div>
				<div>{this.state.data.location}</div>
				<div>{this.state.data.date}</div>
				<Buttons url={this.state.url} id={this.state.data.id}/>
			</li>
		)
	}
}

export default Event;
