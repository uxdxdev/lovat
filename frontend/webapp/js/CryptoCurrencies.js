import React from 'react';
import axios from 'axios';
import AssetPair from '../js/AssetPair';

class CryptoCurrencies extends React.Component {

	constructor(props) {
  	super(props);
  	this.state = {
			data : this.props.data,
			pollInterval: this.props.pollInterval,
			dataEndpointUrl: '/kraken'
		};
		this.loadDataFromServer = this.loadDataFromServer.bind(this)
	}

	loadDataFromServer(component, apiUrl) {
		axios.get(apiUrl).then(function(res) {
			var dataList = res.data.list;
			component.setState({
				data : dataList
			});
		});
	}

	componentDidMount() {
		this.loadDataFromServer(this, this.state.dataEndpointUrl);
		setInterval(this.loadDataFromServer.bind(null, this, this.state.dataEndpointUrl), this.state.pollInterval);
	}

	render(){
		const dataEndpointUrl = this.state.dataEndpointUrl;
		const dataItems = this.state.data.map(function(item) {
			return <AssetPair data={item} key={item.id} url={dataEndpointUrl}/>
		});

		return (
			<div>
				<h2>Crypto</h2>
				<ul className='DataItemList-list'>
					{dataItems}
				</ul>
			</div>
		)
	}
}

export default CryptoCurrencies;
