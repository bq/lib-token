package com.bq.oss.lib.token.factory;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;

import com.bq.oss.lib.token.TokenGrant;
import com.bq.oss.lib.token.TokenInfo;
import com.bq.oss.lib.token.repository.OneTimeAccessTokenRepository;
import com.bq.oss.lib.token.serializer.TokenSerializer;
import com.bq.oss.lib.token.signer.TokenSigner;
import com.bq.oss.lib.token.model.OneTimeAccessToken;

/**
 * @author Francisco Sanchez
 */
public class BasicTokenFactory implements TokenFactory {

	private final TokenSigner signer;
	private final TokenSerializer serializer;
	private final OneTimeAccessTokenRepository oneTimeAccessTokenRepository;
	private final Clock clock;

	public BasicTokenFactory(TokenSigner signer, TokenSerializer serializer,
			OneTimeAccessTokenRepository oneTimeAccessTokenRepository, Clock clock) {
		this.signer = signer;
		this.serializer = serializer;
		this.oneTimeAccessTokenRepository = oneTimeAccessTokenRepository;
		this.clock = clock;
	}

	@Override
	public TokenGrant createToken(TokenInfo info, long expiresIn, String... tags) {
		Instant expireTime = clock.instant().plus(expiresIn, ChronoUnit.SECONDS);
		long expiresAt = expireTime.toEpochMilli();
		String token = serializer.serialize(info, expiresAt, signer);
		if (info.isOneUseToken()) {
			oneTimeAccessTokenRepository
					.save(new OneTimeAccessToken(token, Date.from(expireTime), Arrays.asList(tags)));
		}
		return new TokenGrant(token, expiresIn);
	}
}
