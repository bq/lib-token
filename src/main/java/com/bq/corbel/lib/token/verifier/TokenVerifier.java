package com.bq.corbel.lib.token.verifier;

import com.bq.corbel.lib.token.exception.TokenVerificationException;
import com.bq.corbel.lib.token.reader.TokenReader;

/**
 * @author Alexander De Leon
 * 
 */
public interface TokenVerifier {

	void verify(TokenReader reader) throws TokenVerificationException;

}
