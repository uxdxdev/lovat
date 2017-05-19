import React from 'react';
import axios from 'axios';

class ActionControl extends React.Component {
	constructor(props){
  	super(props);
  	this.state = {
			id : props.id,
			url: props.url
		};

		this.edit = this.edit.bind(this)
		this.remove = this.remove.bind(this)
	}

	edit(){
		alert('Editing event')
	}

	remove(){
		const requestUrlWithParam = this.state.url + '/' + this.state.id;
		alert('Delete event using url ' + requestUrlWithParam)
		axios.delete(requestUrlWithParam).then(function(res) {

		});
	}

	render(){
		return (
			<span>
				<button className='ActionControl-edit' onClick={this.edit}>EDIT</button>
				<button className='ActionControl-remove' onClick={this.remove}>X</button>
			</span>
		)
	}
}

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
		const requestUrlWithParam = this.state.url + '/' + this.state.id;

		return (
			<li className='EventList-item' key={this.state.data.id}>
				<h2 className='EventListItem-name'>{this.state.data.name}</h2>
				<div>{this.state.data.description}</div>
				<div>{this.state.data.location}</div>
				<div>{this.state.data.date}</div>
				<ActionControl url={this.state.url} id={this.state.data.id}/>
				<span>{requestUrlWithParam}</span>
			</li>
		)
	}
}

export default Event;
