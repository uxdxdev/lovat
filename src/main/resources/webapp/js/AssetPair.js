import React from 'react';
import axios from 'axios';

class AssetPair extends React.Component {
	constructor(props){
  	super(props);
  	this.state = {
			data : props.data,
			key: props.key,
			url: props.url
		};
	}

	render(){
		//var jsonObjectKeys = Object.keys(JSON.parse(this.state.data.result));
		//console.log(jsonObjectKeys[0]);
		return (
			<li key={this.state.data.id}>
				<div className='List-item'>
					<h2 className='ListItem-name'>XETHZEUR</h2>
					<div>{this.state.data.result.XETHZEUR.c[0]}</div>
				</div>
			</li>
		)
	}
}

export default AssetPair;
