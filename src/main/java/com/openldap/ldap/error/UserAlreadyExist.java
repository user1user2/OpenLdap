package com.openldap.ldap.error;

import org.apache.logging.log4j.message.Message;

public class UserAlreadyExist extends Exception {
	private static String message;

	public UserAlreadyExist(String message) {
		super(message);
	}

}
