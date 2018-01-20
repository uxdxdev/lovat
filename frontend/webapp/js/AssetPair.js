import React from 'react';
import axios from 'axios';

class AssetPair extends React.Component {
	constructor(props){
  	super(props);
  	this.state = {
            btceur : props.btceur,
			data : props.data,
			key: props.key,
			url: props.url
		};
	}

	render(){

	    let firstAsset = String(this.state.data.pair_name).substring(1, 4);
        let secondAsset = String(this.state.data.pair_name).substring(5, 8);
        let euro = 0;
        let btc = 0;
        let btcEuroPrice = Number(this.state.btceur);
        switch (secondAsset) {
            case "XBT":
                btc = this.state.data.c[0];
                euro = btcEuroPrice;
                break;
            case "EUR":
                euro = this.state.data.c[0];
                btc = euro / btcEuroPrice;
                break;
        }

        euro = Number(euro).toFixed(2);
        btc = Number(btc).toFixed(9);

		return (
			<li key={this.state.data.id}>
				<div className='card mt-2 mb-2'>
                    <div className='row card-body p-2'>
                        <div className='col text-center'>{firstAsset + "/" + secondAsset}</div>
                        <div className='col'>฿{btc}</div>
                        <div className='col'>€{euro}</div>
                    </div>
				</div>
			</li>
		)
	}
}

export default AssetPair;
