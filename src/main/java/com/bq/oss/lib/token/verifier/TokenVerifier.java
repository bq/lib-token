package com.bq.oss.lib.token.verifier;

import com.bq.oss.lib.token.exception.TokenVerificationException;
import com.bq.oss.lib.token.reader.TokenReader;

/**
 * @author Alexander De Leon
 * 
 */
public interface TokenVerifier {

	void verify(TokenReader reader) throws TokenVerificationException;

}
