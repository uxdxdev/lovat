import React from 'react';
import Navbar from '../js/Navbar'
import Header from '../js/Header'

class Index extends React.Component {
	render(){
		return (
			<div>
				<Navbar/>
				<Header/>
				<div className="LandingPage">
					<p>PLEASE ALLOW TIME FOR SERVICES TO SPIN UP WHEN ACCESSING THE DASHBOARD</p>
				</div>
			</div>
		)
	}
}

export default Index;
