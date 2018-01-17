package com.bitbosh.lovat.webgateway.core;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.net.URI;

public class NotAuthorizedExceptionHandler implements ExceptionMapper<NotAuthorizedException> {

	@Override
	public Response toResponse(NotAuthorizedException exception) {
        return Response.seeOther(URI.create("/login")).cookie(new NewCookie(HttpHeaders.AUTHORIZATION, "")).header(HttpHeaders.AUTHORIZATION, "").build();
	}

}
