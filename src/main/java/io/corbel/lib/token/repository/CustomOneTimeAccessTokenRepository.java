package com.bq.corbel.lib.token.repository;

import com.bq.corbel.lib.token.model.OneTimeAccessToken;

/**
 * @author Alberto J. Rubio
 */
public interface CustomOneTimeAccessTokenRepository {

	OneTimeAccessToken findAndRemove(String id);

	void deleteByTags(String... tags);
}
