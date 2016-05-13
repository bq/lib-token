package com.bq.corbel.lib.token.serializer;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bq.corbel.lib.token.TokenInfo;
import com.bq.corbel.lib.token.signer.TokenSigner;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Francisco Sanchez
 */
public class Base64TokenSerializerTest {
	private static final String SERIALIZED_TOKEN = "Serialized.token";
	private static final String BASE64_SERIALIZED_TOKEN = "U2VyaWFsaXplZC50b2tlbg";
	private static final String SIGN = "Signature";
	private final long EXPIRE_TIME = 10L;
	private final String HEX_EXPIRE_TIME = Long.toHexString(EXPIRE_TIME);

	private TokenInfo tokenInfo;
	private TokenSigner tokenSigner;

	@Before
	public void setup() {
		tokenInfo = mock(TokenInfo.class);
		when(tokenInfo.serialize()).thenReturn(SERIALIZED_TOKEN);

		tokenSigner = mock(TokenSigner.class);
		when(tokenSigner.sign(BASE64_SERIALIZED_TOKEN + "." + HEX_EXPIRE_TIME)).thenReturn(SIGN);
	}

	@Test
	public void testBase64TokenSerializer() {
		TokenSerializer testBase64TokenSerializer = new Base64TokenSerializer();
		String serializedToken = testBase64TokenSerializer.serialize(tokenInfo, EXPIRE_TIME, tokenSigner);
		assertThat(serializedToken).isEqualTo(BASE64_SERIALIZED_TOKEN + "." + HEX_EXPIRE_TIME + "." + SIGN);
	}
}
