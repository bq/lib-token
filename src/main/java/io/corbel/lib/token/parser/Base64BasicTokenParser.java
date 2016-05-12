package com.bq.corbel.lib.token.parser;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import com.bq.corbel.lib.token.TokenInfo;
import com.bq.corbel.lib.token.exception.TokenVerificationException;
import com.bq.corbel.lib.token.serializer.Base64TokenSerializer;
import com.bq.corbel.lib.token.verifier.TokenVerifier;
import org.apache.commons.lang3.Validate;

import com.bq.corbel.lib.token.reader.TokenReader;

/**
 * @author Alexander De Leon
 * 
 */
public class Base64BasicTokenParser implements TokenParser {

    private final List<TokenVerifier> verifiers;

    public Base64BasicTokenParser(List<TokenVerifier> verifiers) {
        this.verifiers = verifiers;
    }

    @Override
    public TokenReader parseAndVerify(String token) throws TokenVerificationException {
        try {
            TokenReader reader = new Reader(token);
            for (TokenVerifier verifier : verifiers) {
                verifier.verify(reader);
            }
            return reader;
        } catch (IllegalArgumentException e) {
            throw new TokenVerificationException("Invalid token", e);
        }
    }

    private static class Reader implements TokenReader {

        private final String token;
        private final TokenInfo info;
        private final long expire;
        private final String signature;

        public Reader(String token) throws TokenVerificationException {
            try {
                this.token = token;
                String[] parts = token.split(Base64TokenSerializer.SEPARATOR_REGEX);
                Validate.isTrue(parts.length == 3);
                info = TokenInfo.deserialize(decode(parts[0]));
                expire = Long.parseLong(parts[1], 16);
                signature = decode(parts[2]);
            } catch (UnsupportedEncodingException e) {
                throw new TokenVerificationException("Encoding problem", e);
            }
        }

        @Override
        public TokenInfo getInfo() {
            return info;
        }

        @Override
        public long getExpireTime() {
            return expire;
        }

        @Override
        public String getSignature() {
            return signature;
        }

        @Override
        public String getToken() {
            return token;
        }

        private String decode(String string) throws UnsupportedEncodingException {
            return new String(Base64.getUrlDecoder().decode(string.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        }

    }
}
