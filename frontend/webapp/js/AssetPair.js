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
				<div className='card'>
					<h3 className='ListItem-name'>{this.state.data.pair_name}</h3>
					<div>24hr High 	{this.state.data.h[0]}</div>
					<div>Last Trade {this.state.data.c[0]}</div>
					<div>24hr Low 	{this.state.data.l[0]}</div>
				</div>
			</li>
		)
	}
}

export default AssetPair;
