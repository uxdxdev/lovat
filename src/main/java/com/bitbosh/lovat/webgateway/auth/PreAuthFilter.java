package com.bitbosh.lovat.webgateway.auth;

import org.glassfish.jersey.internal.util.Base64;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

@Priority(Priorities.AUTHENTICATION - 1)
public class PreAuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext request) throws IOException {

        boolean hasValidHeader = false;
        if (request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            final String header = request.getHeaderString(HttpHeaders.AUTHORIZATION);
            if (header.toLowerCase().startsWith("basic")) {
                hasValidHeader = true;
            }
        }

        // add a default set of credentials to the request so that the server does not
        // automatically respond with a 401 causing the browser to display the sign-in popup.
        if (!hasValidHeader) {

            // check cookies for authorization details
            String hashedValue = "";
            if(request.getCookies().containsKey(HttpHeaders.AUTHORIZATION)){
                hashedValue = request.getCookies().get(HttpHeaders.AUTHORIZATION).getValue();
            }

            if(!hashedValue.isEmpty()) {
                request.getHeaders().putSingle(HttpHeaders.AUTHORIZATION, hashedValue);
            } else {
                final String defaultUser = "defaultUser";
                final String defaultPassword = "defaultPassword";
                final String base64 = Base64.encodeAsString(defaultUser + ":" + defaultPassword);
                request.getHeaders().putSingle(HttpHeaders.AUTHORIZATION, "Basic " + base64);
            }
        }
    }
}