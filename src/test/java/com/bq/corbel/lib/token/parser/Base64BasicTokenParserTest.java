package com.bq.corbel.lib.token.parser;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Base64;
import java.util.Collections;

import com.bq.corbel.lib.token.TokenInfo;
import com.bq.corbel.lib.token.exception.TokenVerificationException;
import com.bq.corbel.lib.token.model.TokenType;
import com.bq.corbel.lib.token.verifier.TokenVerifier;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.bq.corbel.lib.token.reader.TokenReader;

/**
 * @author Alexander De Leon
 * 
 */
public class Base64BasicTokenParserTest {

	private static final TokenInfo TEST_TOKEN_INFO = TokenInfo.newBuilder().setType(TokenType.CODE).setUserId("user")
			.setClientId("client").build();
	private static final TokenInfo TEST_ONE_TIME_TOKEN_INFO = TokenInfo.newBuilder().setType(TokenType.CODE)
			.setUserId("user").setClientId("client").setOneUseToken(true).build();
	private static final long TEST_EXIPIRE = 10l;
	private static final String TEST_SIGNATURE = "signature";
	private static final String TEST_TOKEN;
	private static final String ONE_TIME_TEST_TOKEN;

	static {
		TEST_TOKEN = new String(Base64.getUrlEncoder().withoutPadding().encode(TEST_TOKEN_INFO.serialize().getBytes()))
				+ "." + Long.toHexString(TEST_EXIPIRE) + "."
				+ new String(Base64.getUrlEncoder().withoutPadding().encode(TEST_SIGNATURE.getBytes()));

		ONE_TIME_TEST_TOKEN = new String(Base64.getUrlEncoder().withoutPadding()
				.encode(TEST_ONE_TIME_TOKEN_INFO.serialize().getBytes()))
				+ "."
				+ Long.toHexString(TEST_EXIPIRE)
				+ "."
				+ new String(Base64.getUrlEncoder().withoutPadding().encode(TEST_SIGNATURE.getBytes()));
	}

	private Base64BasicTokenParser parser;
	private TokenVerifier verifierMock;

	@Before
	public void setup() {
		verifierMock = mock(TokenVerifier.class);
		parser = new Base64BasicTokenParser(Collections.singletonList(verifierMock));
	}

	@Test
	public void testParseOk() throws TokenVerificationException {
		TokenReader reader = parser.parseAndVerify(TEST_TOKEN);
		assertThat(reader.getExpireTime()).isEqualTo(TEST_EXIPIRE);
		assertThat(reader.getInfo()).isEqualsToByComparingFields(TEST_TOKEN_INFO);
		assertThat(reader.getSignature()).isEqualTo(TEST_SIGNATURE);
		assertThat(reader.getToken()).isEqualTo(TEST_TOKEN);
		verify(verifierMock).verify(reader);
	}

	@Test
	public void testOneTimeTokenParseOk() throws TokenVerificationException {
		TokenReader reader = parser.parseAndVerify(ONE_TIME_TEST_TOKEN);
		assertThat(reader.getExpireTime()).isEqualTo(TEST_EXIPIRE);
		assertThat(reader.getInfo()).isEqualsToByComparingFields(TEST_ONE_TIME_TOKEN_INFO);
		assertThat(reader.getSignature()).isEqualTo(TEST_SIGNATURE);
		assertThat(reader.getToken()).isEqualTo(ONE_TIME_TEST_TOKEN);
		verify(verifierMock).verify(reader);
	}

	@Test(expected = TokenVerificationException.class)
	public void testParseWithInvalidStructure() throws TokenVerificationException {
		parser.parseAndVerify("aaa.xxx");
	}

	@Test(expected = TokenVerificationException.class)
	public void testParseWithInvalidInfo() throws TokenVerificationException {
		parser.parseAndVerify("aaa.xxx.yyy");
	}

	@Test(expected = TokenVerificationException.class)
	public void testFailVerifier() throws TokenVerificationException {
		Mockito.doThrow(new TokenVerificationException("test exception")).when(verifierMock)
				.verify(Mockito.any(TokenReader.class));
		parser.parseAndVerify(TEST_TOKEN);
	}
}
