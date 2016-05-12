package com.bq.corbel.lib.token.factory;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.bq.corbel.lib.token.TokenGrant;
import com.bq.corbel.lib.token.TokenInfo;
import com.bq.corbel.lib.token.repository.OneTimeAccessTokenRepository;
import com.bq.corbel.lib.token.serializer.TokenSerializer;
import com.bq.corbel.lib.token.signer.TokenSigner;

/**
 * @author Francisco Sanchez
 */
public class BasicTokenFactoryTest {
	private static final String SERIALIZED_CODE = "AZERTY";
	private static final String SERIALIZED_TOKEN = "QWERTYUIOP";

	private final long ONE_TIME_ACCESS_TOKEN_DURATION_IN_SEC = 10;
	private final long ACCESS_TOKEN_DURATION_IN_SEC = 20;

	private final long ONE_TIME_ACCESS_TOKEN_DURATION_IN_MILLIS = TimeUnit.SECONDS
			.toMillis(ONE_TIME_ACCESS_TOKEN_DURATION_IN_SEC);
	private final long ACCESS_TOKEN_DURATION_IN_MILLIS = TimeUnit.SECONDS.toMillis(ACCESS_TOKEN_DURATION_IN_SEC);

	private TokenFactory tokenFactory;
	private TokenInfo tokenInfo;
	private OneTimeAccessTokenRepository oneTimeAccessTokenRepository;

	@Before
	public void setup() {

		tokenInfo = mock(TokenInfo.class);
		TokenSigner tokenSigner = mock(TokenSigner.class);
		TokenSerializer tokenSerializer = mock(TokenSerializer.class);
		oneTimeAccessTokenRepository = mock(OneTimeAccessTokenRepository.class);

		tokenFactory = new BasicTokenFactory(tokenSigner, tokenSerializer, oneTimeAccessTokenRepository, Clock.fixed(
				Instant.EPOCH, ZoneOffset.UTC));

		when(tokenSerializer.serialize(tokenInfo, ONE_TIME_ACCESS_TOKEN_DURATION_IN_MILLIS, tokenSigner)).thenReturn(
				SERIALIZED_CODE);
		when(tokenSerializer.serialize(tokenInfo, ACCESS_TOKEN_DURATION_IN_MILLIS, tokenSigner)).thenReturn(
				SERIALIZED_TOKEN);
	}

	@Test
	public void testBasicTokenFactoryWhenCode() {
		TokenGrant token = tokenFactory.createToken(tokenInfo, ONE_TIME_ACCESS_TOKEN_DURATION_IN_SEC);
		assertThat(token.getAccessToken()).isEqualTo(SERIALIZED_CODE);
		assertThat(token.getExpiresIn()).isEqualTo(ONE_TIME_ACCESS_TOKEN_DURATION_IN_SEC);
	}

	@Test
	public void testBasicTokenFactoryWhenAcccesToken() {
		TokenGrant token = tokenFactory.createToken(tokenInfo, ACCESS_TOKEN_DURATION_IN_SEC);
		assertThat(token.getAccessToken()).isEqualTo(SERIALIZED_TOKEN);
		assertThat(token.getExpiresIn()).isEqualTo(ACCESS_TOKEN_DURATION_IN_SEC);
	}

}
