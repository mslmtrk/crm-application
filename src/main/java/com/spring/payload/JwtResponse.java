package com.spring.payload;

import java.util.Date;

public class JwtResponse {

	private String token;
	
	private String username;
	
	private Date expiryDate;
	
	public JwtResponse() {
		
	}

	public JwtResponse(String token, String username, Date expiryDate) {
		this.token = token;
		this.username = username;
		this.expiryDate = expiryDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

}
