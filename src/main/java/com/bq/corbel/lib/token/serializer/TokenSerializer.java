package com.bq.corbel.lib.token.serializer;

import com.bq.corbel.lib.token.TokenInfo;
import com.bq.corbel.lib.token.signer.TokenSigner;

/**
 * @author Alexander De Leon
 * 
 */
public interface TokenSerializer {

	String serialize(TokenInfo info, long expireTime, TokenSigner signer);

}
