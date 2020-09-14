package com.numbergenerator.security;

public class SecurityConstants {


	private SecurityConstants() {
		
		throw new IllegalStateException("Utility class");

	}
	
	public static final String USER_NAME="user";
	public static final String PASSWORD="{noop}password";
	public static final String PRODUCTS_END_POINT="/api/*";
	public static final String PRODUCTS_ROLE="USER";
	

}
