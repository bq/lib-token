package com.bq.oss.lib.token;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.bq.oss.lib.token.model.TokenType;

import java.util.Arrays;

/**
 * @author Cristian del Cerro
 */
public class TokenInfoTest {

	@Test
	public void testDeserialize() {
		String serialized = TokenInfo.newBuilder().setType(TokenType.TOKEN).setUserId("User").setClientId("Client")
				.setDeviceId("Device").setOneUseToken(false).setGroups(Arrays.asList("Admins", "Users")).build().serialize();

		TokenInfo tokenInfoTest = TokenInfo.deserialize(serialized);

		assertThat(tokenInfoTest.getTokenType()).isEqualTo(TokenType.TOKEN);
		assertThat(tokenInfoTest.getUserId()).isEqualTo("User");
		assertThat(tokenInfoTest.getClientId()).isEqualTo("Client");
		assertThat(tokenInfoTest.getDeviceId()).isEqualTo("Device");
		assertThat(tokenInfoTest.getGroups()).contains("Users", "Admins");
		assertThat(tokenInfoTest.isOneUseToken()).isEqualTo(false);
	}

	@Test
	public void testOneTimeTokenDeserializer() {
		String serialized = TokenInfo.newBuilder().setType(TokenType.CODE).setUserId("User").setClientId("Client")
				.setOneUseToken(true).setDomainId("Corbel").setState("State").build().serialize();

		TokenInfo tokenInfoTest = TokenInfo.deserialize(serialized);

		assertThat(tokenInfoTest.getTokenType()).isEqualTo(TokenType.CODE);
		assertThat(tokenInfoTest.getUserId()).isEqualTo("User");
		assertThat(tokenInfoTest.getClientId()).isEqualTo("Client");
		assertThat(tokenInfoTest.getDomainId()).isEqualTo("Corbel");
		assertThat(tokenInfoTest.getState()).isEqualTo("State");
		assertThat(tokenInfoTest.isOneUseToken()).isEqualTo(true);
	}

}
