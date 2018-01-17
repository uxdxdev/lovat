import React from 'react';
import axios from 'axios';
import Navbar from '../js/Navbar'
import Header from '../js/Header'

class Login extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			username: '',
			password: '',
            loginStatus: 'test'
		};

		this.onSubmit = this.onSubmit.bind(this);
        this.onUsernameChange = this.onUsernameChange.bind(this);
		this.onPasswordChange = this.onPasswordChange.bind(this);
	}

	onSubmit(e){
		e.preventDefault();
        this.updateNotification('Please wait...');

        axios({
            method: 'post',
            url: '/auth',
            auth: {
                username: this.state.username,
                password: this.state.password
            }
        }).then(function (response) {
            if(response.status === 201) {
                window.location.replace('/dashboard');
            } else {
                // reset the values in the form
                this.updateNotification('Email or password invalid.');
            }
        }).catch((error) => {
            this.updateNotification('Email or password invalid.');
        });

        this.setState({
            username: '',
            password: ''
        });
	}

	updateNotification(notification) {
        this.setState({loginStatus: notification});
    }

	onUsernameChange(e){
		this.setState({username: e.target.value})
	}

	onPasswordChange(e){
		this.setState({password: e.target.value})
	}

	render() {
		return (
			<div>
				<Navbar/>
				<Header/>
				<div className='LoginForm'>
					<form onSubmit={this.onSubmit}>
						<input type='text' placeholder='Username' required='true' value={this.state.username} onChange={this.onUsernameChange} />
						<input type='password' placeholder='Password' required='true' value={this.state.password} onChange={this.onPasswordChange} />
						<div id="ButtonHolder">
							<button type='submit'>Sign In</button>
						</div>
					</form>
                    <div class="alert alert-warning" role="alert">
                        {this.state.loginStatus}
                    </div>
				</div>

			</div>
        )
	}
}

export default Login;
