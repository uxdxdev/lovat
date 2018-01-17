import React from 'react';

class Navbar extends React.Component {
	render(){
		return (
      <div id='Navbar'>
          <div id='TravisBuildStatus'>
              <a href="https://travis-ci.org/damorton/lovat" target="_blank" ><img src="https://api.travis-ci.org/damorton/lovat.svg?branch=master" alt="Travis build status" /></a>
          </div>
          <div id='NavButton'>
              <a href="/login">Login</a>
          </div>
      </div>
		)
	}
}

export default Navbar;
