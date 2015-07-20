package com.bq.oss.lib.token.ioc;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bq.oss.lib.token.parser.Base64BasicTokenParser;
import com.bq.oss.lib.token.parser.TokenParser;
import com.bq.oss.lib.token.serializer.Base64TokenSerializer;
import com.bq.oss.lib.token.serializer.TokenSerializer;
import com.bq.oss.lib.token.signer.HmacSha1TokenSigner;
import com.bq.oss.lib.token.signer.TokenSigner;
import com.bq.oss.lib.token.verifier.ExpirationTokenVerifier;
import com.bq.oss.lib.token.verifier.SignatureTokenVerifier;
import com.bq.oss.lib.token.verifier.TokenVerifier;

/**
 * Created by Alberto J. Rubio
 */
@Configuration
public class TokenIoc {

	@Bean
	public ExpirationTokenVerifier expireTokenVerifier() {
		return new ExpirationTokenVerifier();
	}

	@Bean
	public SignatureTokenVerifier signatureTokenVerifier(TokenSigner signer, TokenSerializer serializer) {
		return new SignatureTokenVerifier(signer, serializer);
	}

	@Bean
	public TokenSerializer tokenSerializer() {
		return new Base64TokenSerializer();
	}

	@Bean
	public TokenSigner tokenSigner(@Value("${token.signatureKey}") String signatureKey) {
		try {
			return new HmacSha1TokenSigner(signatureKey);
		} catch (Exception e) {
			throw new BeanCreationException("unable to create token signer", e);
		}
	}

	@Bean
	public TokenParser tokenParser(Collection<TokenVerifier> verifiers) {
		return new Base64BasicTokenParser(new ArrayList<>(verifiers));
	}

}
