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

	}

	remove(){
		const requestUrlWithParam = this.state.url + '/' + this.state.id;
		axios.delete(requestUrlWithParam).then(function(res) {

		});
	}

	render(){
		return (
			<div className='ActionControl'>
				<button className='ActionControl-edit' onClick={this.edit}>EDIT</button>
				<button className='ActionControl-remove' onClick={this.remove}>X</button>
			</div>
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
		return (
			<li key={this.state.data.id}>
				<div className='card'>
					<h2 className='ListItem-name'>{this.state.data.name}</h2>
					<div>{this.state.data.description}</div>
					<div>{this.state.data.location}</div>
					<div>{this.state.data.date}</div>
					<ActionControl url={this.state.url} id={this.state.data.id}/>
				</div>
			</li>
		)
	}
}

export default Event;
