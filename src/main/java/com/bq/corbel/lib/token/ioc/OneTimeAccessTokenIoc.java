package com.bq.corbel.lib.token.ioc;

import java.time.Clock;

import com.bq.corbel.lib.mongo.config.MongoCommonRepositoryFactoryBean;
import com.bq.corbel.lib.token.factory.BasicTokenFactory;
import com.bq.corbel.lib.token.factory.TokenFactory;
import com.bq.corbel.lib.token.repository.OneTimeAccessTokenRepository;
import com.bq.corbel.lib.token.serializer.TokenSerializer;
import com.bq.corbel.lib.token.signer.TokenSigner;
import com.bq.corbel.lib.token.verifier.OneTimeAccessTokenVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author Alexander De Leon
 *
 */
@Configuration
@EnableMongoRepositories(value = { "com.bq.corbel.lib.token.repository" }, repositoryFactoryBeanClass = MongoCommonRepositoryFactoryBean.class)
public class OneTimeAccessTokenIoc {

	@Bean
	public OneTimeAccessTokenVerifier oneTimeAccessTokenVerifier(
			OneTimeAccessTokenRepository oneTimeAccessTokenRepository) {
		return new OneTimeAccessTokenVerifier(oneTimeAccessTokenRepository);
	}

	@Bean
	public TokenFactory tokenFactory(TokenSigner tokenSigner, TokenSerializer tokenSerializer,
			OneTimeAccessTokenRepository oneTimeAccessTokenRepository) {
		return new BasicTokenFactory(tokenSigner, tokenSerializer, oneTimeAccessTokenRepository, Clock.systemUTC());
	}

}
