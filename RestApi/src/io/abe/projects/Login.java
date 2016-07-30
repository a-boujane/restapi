package io.abe.projects;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.internal.util.Base64;

/*
 * This is a password validation endpoint
 * It Currently only support Basic Authentication
 */

@Path("/login")
public class Login {

	@GET
	public Response login(@Context ContainerRequestContext req) {
		// Retrieving the Basic Auth information from the Authentication Header
		String auth = req.getHeaderString("authorization");
		if (auth == null)
			return Response.status(401).build();
		// For Basic Auth, the header should have two strings separated by a
		// space: "Basic" and the email:password encoded in Base64
		// Here, we are going to retrieve the email:password, and then decode
		// them
		String pw = auth.split(" ")[1];
		pw = Base64.decodeAsString(pw);

		// At this point, we should be ready to send the emailAddress and the
		// password to the DB for validation
		String[] creds = pw.split(":");
		boolean valid = MongoConnector.validator(creds[0], creds[1]);
		return valid ? Response.ok("Login Successful").build()
				: Response.status(401).entity("Email/Password incorrect").build();
	}

}
