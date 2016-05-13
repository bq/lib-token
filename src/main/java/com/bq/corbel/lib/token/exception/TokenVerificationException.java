package com.bq.corbel.lib.token.exception;

/**
 * @author Alexander De Leon
 * 
 */
public class TokenVerificationException extends Exception {

	private static final long serialVersionUID = 1L;

	public TokenVerificationException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenVerificationException(String message) {
		super(message);
	}

	public static class TokenExpired extends TokenVerificationException {

		private static final long serialVersionUID = 1L;

		public TokenExpired() {
			super("Token expired");
		}

	}

	public static class TokenNotFound extends TokenVerificationException {

		private static final long serialVersionUID = 1L;

		public TokenNotFound() {
			super("Token not found");
		}

	}

	public static class InvalidSignature extends TokenVerificationException {
		private static final long serialVersionUID = 1L;

		public InvalidSignature() {
			super("Invalid token signature");
		}
	}

	public static class UserNotExists extends TokenVerificationException {
		private static final long serialVersionUID = 1L;

		public UserNotExists() {
			super("User not exists");
		}
	}

}
