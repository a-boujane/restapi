package io.abe.projects;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
/*
 * This represents the User Object stored in the DB, annotated with JAXB and Morphia annotations
 */
@Entity
@XmlRootElement
public class User {
	@Id
	@XmlElement(required = true)
	private String email;
	@XmlElement(required = true)
	private String name;
	@XmlElement(required = true)
	private String password;
	private Date lastLogin;

	public String getEmail() {
		return email;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getName() {
		return name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

}
