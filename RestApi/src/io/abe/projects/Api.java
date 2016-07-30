package io.abe.projects;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/*
 * This is a JSON API, that allows CRUD operations on users in a DataBase
 */
@Path("/users")
public class Api {
	/*
	 * Lists all users in the DB (Including their names, email, password, and
	 * lastLogins...
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		System.out.println("All Users");
		List<User> users  = MongoConnector.getUsers();
		return users;

	}

	/*
	 * Returns a single resource by Email Address. (Including all 4 of its
	 * attributes)
	 */
	@GET
	@Path("{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam(value = "email") String email) {
		User result = MongoConnector.getUser(email);
		return result!=null? Response.ok(result).build():Response.status(404).build();

	}

	/*
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * THIS METHOD WILL OVERWRITE ANY EXISTING USERS WITH THE SAME EMAIL ADDRESS
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * we could technically verify first if anyone else with the same email
	 * address exists first....
	 * 
	 * 
	 * Adds a new user to the Database and returns a 201 Created, including the
	 * location of the new resource.
	 */

	@POST
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(User user, @Context UriInfo uriInfo) throws URISyntaxException {
		MongoConnector.addUser(user);
		URI uri = uriInfo.getAbsolutePathBuilder().path(user.getEmail()).build();
		return Response.created(uri).build();

	}

	/*
	 * This Method gets called when the server receives a PUT request. the email
	 * in the URI has to match the one provided in the JSON body, in which case,
	 * only the name and the password will get modified. The lastLogin can only
	 * be modified via the /login endpoint.
	 */

	@PUT
	@Path("{email}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyUser(@PathParam(value = "email") String email, User user) {
		User result = MongoConnector.modifyUser(email, user);
		return result != null ? Response.ok(result).build() : Response.status(400).build();
	}

	/*
	 * Requires no body, This method deletes the user with the email passed in
	 * the URI from the DataBase. If the user does not exist, it would return a
	 * 404 if the user gets deleted successfully, it returns a 200 OK, with the
	 * JSON of the user in the Body.
	 */
	@DELETE
	@Path("{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam(value = "email") String email) {
		User result = MongoConnector.deleteUser(email);
		return result != null ? Response.ok(result).build() : Response.status(404).build();
	}

}
