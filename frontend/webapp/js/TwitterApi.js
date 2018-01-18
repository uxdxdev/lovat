import React from 'react';
import axios from 'axios';
import Tweet from '../js/Tweet'

class TwitterApi extends React.Component {

	constructor(props) {
  	super(props);
  	this.state = {
			tweets : this.props.tweets,
			pollInterval: this.props.pollInterval,
			tweetsEndpointUrl: '/tweets'
		};

		this.loadTweetsFromServer = this.loadTweetsFromServer.bind(this)
	}

	loadTweetsFromServer(component, twitterApiUrl) {
		axios.get(twitterApiUrl).then(function(res) {
			var tweetList = res.data.list;
			component.setState({
				tweets : tweetList
			});

			if(tweetList !== null){
                setInterval(this.loadTweetsFromServer.bind(null, this, this.state.tweetsEndpointUrl), this.state.pollInterval);
            }
		});
	}

	componentDidMount() {
		this.loadTweetsFromServer(this, this.state.tweetsEndpointUrl);
	}

	render(){
	    let tweets = "Error loading tweets...";
	    if(this.state.tweets !== null) {
	        tweets = this.state.tweets.map(function (tweet) {
                return <Tweet data={tweet} key={tweet.id} url={this.state.tweetsEndpointUrl}/>
            });
        }
		return (
			<div>
				<h2 className="text-center">Twitter</h2>
				<ul>
					{tweets}
				</ul>
			</div>
		)
	}
}

export default TwitterApi;
