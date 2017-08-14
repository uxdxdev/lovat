package com.bitbosh.lovat.webgateway.auth;

import java.util.Optional;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;

import com.bitbosh.lovat.webgateway.core.User;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class CustomAuthenticator implements Authenticator<BasicCredentials, User> {

	@Override
	public Optional<User> authenticate(BasicCredentials credentials) {
		// request user credentials from User service
		
		if ("crimson".equals(credentials.getPassword())) {		
			User user = new User(credentials.getUsername(), credentials.getPassword());
			return Optional.of(user);
		} else {
			throwNotAuthorizedExceptionToRedirectToLogin();
			return Optional.empty();
		}
	}

	private void throwNotAuthorizedExceptionToRedirectToLogin() {		
		throw new NotAuthorizedException(Response.Status.UNAUTHORIZED);
	}
}
