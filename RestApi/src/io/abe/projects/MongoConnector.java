package io.abe.projects;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mongodb.MongoClient;

public class MongoConnector {

	/*
	 * This bloc is to initiate the DB connection upon startup
	 */
	private static MongoClient client;
	private static Datastore dataStore;
	private static Morphia morphia;

	// This static bloc executes only when the class gets loaded fist. It
	// creates the DB connection, which can be user later on to perform
	// operations on items in the DB

	static {

		client = new MongoClient("10.0.0.25");
		morphia = new Morphia();
		dataStore = morphia.createDatastore(client, "uzers");
	}

	// This allows to add one user to the DB.
	// If another user with the same email address exists already, it will be
	// overwritten
	// We could potentially add a "Validity" test before we overwirte the
	// existing user in case of a conflict

	public static User addUser(User user) {
		user.setLastLogin(new Date(0));
		dataStore.save(user);
		return user;
	}

	// This method compares the email with the user's email for verification
	// first, then modifies the DB user to match the user provided in the
	// parameters.

	public static User modifyUser(String email, User user) {
		if (email == null || !email.equals(user.getEmail()))
			return null;
		Query<User> q = dataStore.createQuery(User.class).filter("email", user.getEmail());
		UpdateOperations<User> up = dataStore.createUpdateOperations(User.class).set("name", user.getName())
				.set("password", user.getPassword());
		dataStore.update(q, up);
		return dataStore.find(User.class, "email", user.getEmail()).get();
	}

	// this Method deletes the user with the email address in the parameter
	// If The user is indeed in teh DB, it returns the User object. Otehrwise,
	// it returns null

	public static User deleteUser(String email) {
		Query<User> q = dataStore.find(User.class, "email", email);
		User result = q.get();
		dataStore.delete(q);
		return result;
	}

	/*
	 * The following method returns a list of ALL users in the DB
	 */

	public static List<User> getUsers() {
		Query<User> q = dataStore.createQuery(User.class);
		return q.asList();
	}

	/*
	 * the following Method returns one single user based on the email address
	 * provided it returns null if the user does not exist
	 */
	public static User getUser(String email) {
		Query<User> q = dataStore.createQuery(User.class).filter("email", email);
		return q.get();
	}

	/*
	 * this method is used by the "login" endpoint to validate the login, and
	 * changes the "lastLogin" attribute returns true if the login is
	 * successful. false otehrwise.
	 */

	public static boolean validator(String email, String pw) {
		System.out.println("Start Mongo Validator");
		User user = getUser(email);

		if (user != null && user.getPassword().equals(pw)) {
			System.out.println("User login here");
			loginUser(email);
			return true;
		}
		return false;
	}

	/*
	 * This private method is only meant to be called when the authentication is
	 * successful, in order to change the lastLogin attribute in the Database
	 */

	private static void loginUser(String email) {
		Query<User> q = dataStore.createQuery(User.class).filter("email", email);
		UpdateOperations<User> up = dataStore.createUpdateOperations(User.class).set("lastLogin", new Date());
		dataStore.update(q, up);
	}

}
