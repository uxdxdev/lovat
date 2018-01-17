package com.bitbosh.lovat.webgateway.auth;

import org.glassfish.jersey.internal.util.Base64;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        if (!hasValidHeader) {

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