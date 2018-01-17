package com.bitbosh.lovat.webgateway.core;

import java.security.Principal;

public class User implements Principal {

	private String username;
	private String password;

    public User() {
		this.username = null;
		this.password = null;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public String getName() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String passhash) {
		this.password = passhash;
	}
}