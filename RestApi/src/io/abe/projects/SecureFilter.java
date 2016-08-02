package io.abe.projects;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

@Provider
public class SecureFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// We are only securing the users/* endpoints
		
		if (requestContext.getUriInfo().getPath().contains("users")) {
			// Retrieving the Basic Auth information from the Authentication Header
			String auth = requestContext.getHeaderString("authorization");
			if (auth == null) {
				requestContext.abortWith(Response.status(401).build());
			}
			// For Basic Auth, the header should have two strings separated by a
			// space: "Basic" and the email:password encoded in Base64
			// Here, we are going to retrieve the email:password, and then
			// decode
			// them
			else {
				String pw = auth.split(" ")[1];
				pw = Base64.decodeAsString(pw);

				// At this point, we should be ready to send the emailAddress
				// and the
				// password to the DB for validation
				
				String[] creds = pw.split(":");
				boolean valid = MongoConnector.validator(creds[0], creds[1]);
				if (valid)
					return;
				requestContext.abortWith(Response.status(401).entity("Email/Password incorrect").build());
			}
		}
	}

}
