package com.bq.oss.lib.token.reader;

import com.bq.oss.lib.token.TokenInfo;

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
