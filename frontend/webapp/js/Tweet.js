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
				<div className='card mt-2 mb-2  '>
                    <div className='card-body p-2'>
                        <h6 className="card-title text-muted">@{this.state.data.user.screen_name}</h6>
                        <h7 className="card-subtitle text-muted">{this.state.data.user.location} - {this.state.data.created_at}</h7>
                        <p className="card-text">{this.state.data.text}</p>
                    </div>
				</div>
			</li>
		)
	}
}

export default Tweet;
