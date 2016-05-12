package com.bq.corbel.lib.token.reader;

import com.bq.corbel.lib.token.TokenInfo;

/**
 * @author Alexander De Leon
 * 
 */
public interface TokenReader {

	TokenInfo getInfo();

	long getExpireTime();

	String getSignature();

	String getToken();

}
