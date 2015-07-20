package com.bq.oss.lib.token.verifier;

import com.bq.oss.lib.token.exception.TokenVerificationException;
import com.bq.oss.lib.token.reader.TokenReader;
import com.bq.oss.lib.token.serializer.TokenSerializer;
import com.bq.oss.lib.token.signer.TokenSigner;

/**
 * @author Alexander De Leon
 * 
 */
public class SignatureTokenVerifier implements TokenVerifier {

	private final TokenSigner signer;
	private final TokenSerializer serializer;

	public SignatureTokenVerifier(TokenSigner signer, TokenSerializer serializer) {
		this.signer = signer;
		this.serializer = serializer;
	}

	@Override
	public void verify(TokenReader reader) throws TokenVerificationException {
		if (!serializer.serialize(reader.getInfo(), reader.getExpireTime(), signer).equals(reader.getToken())) {
			throw new TokenVerificationException.InvalidSignature();
		}
	}
}
