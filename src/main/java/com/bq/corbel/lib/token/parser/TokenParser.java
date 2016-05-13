package com.bq.corbel.lib.token.parser;

import com.bq.corbel.lib.token.exception.TokenVerificationException;
import com.bq.corbel.lib.token.reader.TokenReader;

/**
 * @author Alexander De Leon
 * 
 */
public interface TokenParser {

	TokenReader parseAndVerify(String token) throws TokenVerificationException;

}
