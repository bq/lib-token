package com.bq.corbel.lib.token;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Cristian del Cerro
 */
public class TokenGrant {

	private final String accessToken;
	private final long expiresIn;

	public TokenGrant(String accessToken, long expiresIn) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
	}

	@JsonProperty("access_token")
	public String getAccessToken() {
		return accessToken;
	}

	@JsonProperty("expires_in")
	public long getExpiresIn() {
		return expiresIn;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accessToken);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TokenGrant)) {
			return false;
		}
		TokenGrant that = (TokenGrant) obj;
		return Objects.equals(this.accessToken, that.accessToken) && Objects.equals(this.expiresIn, that.expiresIn);
	}

	// For JSon Convert purposes
	private TokenGrant() {
		accessToken = null;
		expiresIn = 0L;
	}

}
