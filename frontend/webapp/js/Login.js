import React from 'react';
import axios from 'axios';
import Header from './Header'
import Footer from '../js/Footer'

class Login extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			username: '',
			password: '',
            loginStatus: ''
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
                <Header/>
                <div className="container-fluid">
                    <div className="container col-md-4 col-md-offset-4">
                        <form onSubmit={this.onSubmit}>
                            <div className="form-group">
                                <label for="username">Username</label>
                                <input id="username" className="form-control" type='text' placeholder='Username' required='true' value={this.state.username} onChange={this.onUsernameChange} />
                                <small id="usernameHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                            </div>
                            <div className="form-group">
                                <label for="password">Password</label>
                                <input id="password" className="form-control" type='password' placeholder='Password' required='true' value={this.state.password} onChange={this.onPasswordChange} />
                            </div>
                            <button className="btn btn-primary" type='submit'>Sign In</button>
                        </form>
                    </div>
                    <div className="container col-md-4 col-md-offset-4">
                        <div className="alert alert-warning" role="alert">
                            {this.state.loginStatus}
                        </div>
                    </div>
                </div>
                <Footer/>
			</div>
        )
	}
}

export default Login;
