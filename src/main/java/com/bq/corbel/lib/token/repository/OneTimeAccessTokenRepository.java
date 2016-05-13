package com.bq.corbel.lib.token.repository;

import org.springframework.data.repository.CrudRepository;

import com.bq.corbel.lib.token.model.OneTimeAccessToken;

/**
 * @author Alberto J. Rubio
 * 
 */
public interface OneTimeAccessTokenRepository extends CrudRepository<OneTimeAccessToken, String>,
		CustomOneTimeAccessTokenRepository {

}
