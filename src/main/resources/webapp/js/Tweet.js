import React from 'react';
import axios from 'axios';

class Tweet extends React.Component {
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
				<div className='TweetList-item'>
					<h2 className='TweetListItem-name'>{this.state.data.name}</h2>
					<div>{this.state.data.text}</div>
				</div>
			</li>
		)
	}
}

export default Tweet;
