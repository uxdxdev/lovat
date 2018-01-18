import React from 'react';

class Header extends React.Component {
	render(){
		return (
		    <header className="navbar flex-md-row">
                <div className="nav">
                    <a className="navbar-brand" href="/"><img className="d-inline-block align-top mr-2" src="assets/favicon.png" alt="logo" />Lovat</a>
                    <a className="nav-link" href="https://travis-ci.org/damorton/lovat" target="_blank" ><img className="d-inline-block align-top" src="https://api.travis-ci.org/damorton/lovat.svg?branch=master" alt="Travis build status" /></a>
                </div>
                <div className="nav ml-md-auto">
                    <a className="nav-link" href="/login">Login</a>
                    <a className="nav-link" href="/logout">Logout</a>
                </div>
            </header>
		)
	}
}

export default Header;
