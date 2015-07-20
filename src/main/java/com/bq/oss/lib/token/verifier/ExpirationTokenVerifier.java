package com.bq.oss.lib.token.verifier;

import com.bq.oss.lib.token.exception.TokenVerificationException;
import com.bq.oss.lib.token.reader.TokenReader;

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
