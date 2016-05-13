package com.bq.corbel.lib.token.verifier;

import com.bq.corbel.lib.token.exception.TokenVerificationException;
import com.bq.corbel.lib.token.reader.TokenReader;

/**
 * @author Alexander De Leon
 * 
 */
public class ExpirationTokenVerifier implements TokenVerifier {

	@Override
	public void verify(TokenReader reader) throws TokenVerificationException {
		if (reader.getExpireTime() <= System.currentTimeMillis()) {
			throw new TokenVerificationException.TokenExpired();
		}
	}
}
