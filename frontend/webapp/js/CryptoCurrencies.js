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

			if(dataList !== null){
                setInterval(this.loadDataFromServer.bind(null, this, this.state.dataEndpointUrl), this.state.pollInterval);
            }
		});
	}

	componentDidMount() {
		this.loadDataFromServer(this, this.state.dataEndpointUrl);
	}

	render(){
	    let dataItems = "Error loading crypto tickers...";
	    if(this.state.data !== null) {
            const dataItems = this.state.data.map(function (item) {
                return <AssetPair data={item} key={item.id} url={this.state.dataEndpointUrl}/>
            });
        }
		return (
			<div>
				<h2 className="text-center">Crypto</h2>
				<ul>
					{dataItems}
				</ul>
			</div>
		)
	}
}

export default CryptoCurrencies;
