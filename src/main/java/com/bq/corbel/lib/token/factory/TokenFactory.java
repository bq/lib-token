package com.bq.corbel.lib.token.factory;

import com.bq.corbel.lib.token.TokenGrant;
import com.bq.corbel.lib.token.TokenInfo;

/**
 * @author Alexander De Leon
 * 
 */
public interface TokenFactory {

	/**
	 * Construct a new oauth token with the specified parameters.
	 * 
	 * @param info
	 *            Information encoded on the token
	 * @param expiresIn
	 *            timestamp when this token should expire
	 * @param tags
	 *            list of tags that should be associated with the token. (Only applicable for one-use-only tokens)
	 * @return
	 */
	TokenGrant createToken(TokenInfo info, long expiresIn, String... tags);
}
