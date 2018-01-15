package com.bitbosh.lovat.webgateway.auth;

import java.util.Optional;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;

import com.bitbosh.lovat.webgateway.core.User;

import com.bitbosh.lovat.webgateway.repository.AccountsDao;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.skife.jdbi.v2.DBI;

public class CustomAuthenticator implements Authenticator<BasicCredentials, User> {


    private final AccountsDao userDao;

    public CustomAuthenticator(DBI jdbi) {
        this.userDao = jdbi.onDemand(AccountsDao.class);
    }

    @Override
	public Optional<User> authenticate(BasicCredentials credentials) {

		String password = this.userDao.getPasswordByEmail(credentials.getUsername());
		if(password == null || password.isEmpty()){
            throwNotAuthorizedExceptionRedirectToLogin();
        }

		if (password.equals(credentials.getPassword())) {
			User user = new User(credentials.getUsername(), credentials.getPassword());
			return Optional.of(user);
		} else {
			throwNotAuthorizedExceptionRedirectToLogin();
			return Optional.empty();
		}
	}

	private void throwNotAuthorizedExceptionRedirectToLogin() {
		throw new NotAuthorizedException(Response.Status.UNAUTHORIZED);
	}
}
