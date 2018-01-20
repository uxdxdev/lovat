import React from 'react';
import Header from './Header'
import Footer from "../js/Footer";

class Index extends React.Component {
	render(){
		return (
			<div>
                <Header/>
                <div className="container-fluid container-fluid-center text-center">
                    <p>Server side ReactJS using Java 8 Nashorn</p>
                </div>
                <Footer/>
			</div>
		)
	}
}

export default Index;
