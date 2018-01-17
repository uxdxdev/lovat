package com.bitbosh.lovat.webgateway.core;

import org.eclipse.jetty.security.authentication.FormAuthenticator;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;

public class NotAuthorizedExceptionHandler implements ExceptionMapper<NotAuthorizedException> {

	@Override
	public Response toResponse(NotAuthorizedException exception) {
	    // redirect to login instead of returing 401 and causing the browser sign-in pop-up to be displayed.
        return Response.seeOther(URI.create("/login")).build();
	}

}
