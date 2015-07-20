package com.bq.oss.lib.token.signer;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Alexander De Leon
 * 
 */
public class HmacSha1TokenSigner implements TokenSigner {

	private static final String HMAC_SHA1 = "HmacSHA1";
	private final Mac mac;

	public HmacSha1TokenSigner(String signatureKey) throws NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec signingKey = new SecretKeySpec(signatureKey.getBytes(), HMAC_SHA1);
		this.mac = Mac.getInstance(HMAC_SHA1);
		mac.init(signingKey);
	}

	@Override
	public synchronized String sign(String token) {
		byte[] rawHmac = mac.doFinal(token.getBytes(StandardCharsets.UTF_8));
		return new String(Base64.getUrlEncoder().withoutPadding().encode(rawHmac), StandardCharsets.UTF_8);
	}

}
