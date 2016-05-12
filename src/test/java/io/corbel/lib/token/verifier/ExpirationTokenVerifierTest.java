package com.bq.corbel.lib.token.verifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bq.corbel.lib.token.exception.TokenVerificationException;
import org.junit.Before;
import org.junit.Test;

import com.bq.corbel.lib.token.reader.TokenReader;

/**
 * @author Alexander De Leon
 * 
 */
public class ExpirationTokenVerifierTest {

	private ExpirationTokenVerifier verifier;

	@Before
	public void setup() {
		verifier = new ExpirationTokenVerifier();
	}

	@Test(expected = TokenVerificationException.TokenExpired.class)
	public void testTokenExpired() throws TokenVerificationException {
		TokenReader reader = mock(TokenReader.class);
		when(reader.getExpireTime()).thenReturn(System.currentTimeMillis() - 1);
		verifier.verify(reader);
	}

	@Test
	public void testOk() throws TokenVerificationException {
		TokenReader reader = mock(TokenReader.class);
		when(reader.getExpireTime()).thenReturn(System.currentTimeMillis() + 1000);
		verifier.verify(reader);
	}

}
