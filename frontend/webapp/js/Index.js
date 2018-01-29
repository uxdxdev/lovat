import React from 'react';
import Header from './Header'
import Footer from "../js/Footer";

class Index extends React.Component {
	render(){
		return (
			<div className="wrapper">
                <Header/>
                <section className="container landing-page text-center">
                    <p>Server side ReactJS using Java 8 Nashorn</p>
                </section>
                <Footer/>
			</div>
		)
	}
}

export default Index;
