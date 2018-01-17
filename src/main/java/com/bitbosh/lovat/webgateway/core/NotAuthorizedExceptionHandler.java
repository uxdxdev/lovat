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
        return Response.temporaryRedirect(URI.create("/login")).build();
	}

}
