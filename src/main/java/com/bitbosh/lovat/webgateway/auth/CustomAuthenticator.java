package com.bitbosh.lovat.webgateway.auth;

import com.bitbosh.lovat.webgateway.core.User;
import com.bitbosh.lovat.webgateway.repository.UserDao;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import java.util.Optional;

public class CustomAuthenticator implements Authenticator<BasicCredentials, User> {

    private final UserDao userDao;

    public CustomAuthenticator(DBI jdbi) {

        this.userDao = jdbi.onDemand(UserDao.class);
    }

    @Override
	public Optional<User> authenticate(BasicCredentials credentials) {

        User user = this.userDao.getUserByUsername(credentials.getUsername());
		if(user == null || user.getPassword().isEmpty() || !user.getPassword().equals(credentials.getPassword())) {
            throwNotAuthorizedException();
        } else {
            return Optional.of(user);
        }

        return Optional.empty();
	}

	private void throwNotAuthorizedException() {

        throw new NotAuthorizedException(Response.Status.UNAUTHORIZED);
	}
}
