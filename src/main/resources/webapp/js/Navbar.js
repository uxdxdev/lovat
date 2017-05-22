import React from 'react';

class Navbar extends React.Component {
	render(){
		return (
      <div id='Navbar'>
          <div id='TravisBuildStatus'>
            <img src="https://api.travis-ci.org/damorton/dropwizardheroku-webgateway.svg?branch=master" alt="Travis build status" />
          </div>
          <div id='LoginButton'>
            <a href="/login">Login</a>
          </div>
      </div>
		)
	}
}

export default Navbar;
