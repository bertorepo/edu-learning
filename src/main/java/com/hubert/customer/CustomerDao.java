package com.hubert.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.hubert.annotations.PasswordMatch;

public class CustomerDao {
	@NotBlank(message = "Username must not be empty")
	private String username;
	@NotBlank(message = "Email must not be empty")
	@Email(message = "Please provide a valid email")
	private String email;
	@NotBlank(message = "Password must not be empty")
	@Size(min = 3, message = "Password should be at least min 3 characters")
	@PasswordMatch
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "CustomerDao [username=" + username + ", email=" + email + ", password=" + password + "]";
	}
	
	

}
