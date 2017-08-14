package com.bitbosh.lovat.webgateway.core;

import java.net.URI;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotAuthorizedExceptionHandler implements ExceptionMapper<NotAuthorizedException> {

	@Override
	public Response toResponse(NotAuthorizedException exception) {		
		return Response.temporaryRedirect(URI.create("/login")).build();
	}

}
