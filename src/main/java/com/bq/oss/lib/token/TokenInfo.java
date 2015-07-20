package com.bq.oss.lib.token;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.*;
import org.apache.commons.lang3.Validate;

import com.bq.oss.lib.token.model.TokenType;

/**
 * @author Alexander De Leon
 * 
 */
public class TokenInfo {

    private static final String TYPE = "type";
    private static final String USER_ID = "userId";
    private static final String CLIENT_ID = "clientId";
    private static final String DEVICE_ID = "deviceId";
    private static final String ONE_USE = "isOneUse";
    private static final String STATE = "state";
    private static final String DOMAIN_ID = "domainId";
    private static final String GROUPS = "groups";

    private final JsonObject data;

    public static TokenInfo deserialize(String asAString) {
        Validate.notEmpty(asAString);
        try {
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(asAString);
            Validate.isTrue(json.isJsonObject());
            return new TokenInfo(json.getAsJsonObject());
        } catch (JsonParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private TokenInfo(JsonObject data) {
        this.data = data;
    }

    public TokenType getTokenType() {
        return TokenType.valueOf(getAsString(TYPE).toUpperCase());
    }

    public String getUserId() {
        return getAsString(USER_ID);
    }

    public String getClientId() {
        return getAsString(CLIENT_ID);
    }

    public String getDeviceId() {
        return getAsString(DEVICE_ID);
    }

    public String getState() {
        return getAsString(STATE);
    }

    public String getDomainId() {
        return getAsString(DOMAIN_ID);
    }

    public List<String> getGroups() {
        return getAsArrayOfStrings(GROUPS);
    }

    public boolean isOneUseToken() {
        Boolean val = getAsBoolean(ONE_USE);
        return val != null ? val : false;
    }

    public String serialize() {
        return data.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TokenInfo)) {
            return false;
        }
        TokenInfo that = (TokenInfo) obj;
        return Objects.equals(this.data.toString(), that.data.toString());
    }

    private String getAsString(String key) {
        return data.has(key) ? data.get(key).getAsString() : null;
    }

    private Boolean getAsBoolean(String key) {
        return data.has(key) ? data.get(key).getAsBoolean() : null;
    }

    private List<String> getAsArrayOfStrings(String key) {
        List<String> groups = new ArrayList<>();
        if(data.has(key)) {
            for(JsonElement group : data.get(key).getAsJsonArray()) {
                groups.add(group.getAsString());
            }
        }
        return groups;
    }

    public static class Builder {
        private final JsonObject data;

        private Builder() {
            data = new JsonObject();
        }

        public Builder setType(TokenType type) {
            data.add(TYPE, new JsonPrimitive(type.name()));
            return this;
        }

        public Builder setUserId(String userId) {
            data.add(USER_ID, new JsonPrimitive(userId));
            return this;
        }

        public Builder setClientId(String clientId) {
            data.add(CLIENT_ID, new JsonPrimitive(clientId));
            return this;
        }

        public Builder setDeviceId(String deviceIdId) {
            data.add(DEVICE_ID, new JsonPrimitive(deviceIdId));
            return this;
        }

        public Builder setOneUseToken(boolean isOneUse) {
            data.add(ONE_USE, new JsonPrimitive(isOneUse));
            return this;
        }

        public Builder setState(String state) {
            data.add(STATE, new JsonPrimitive(state));
            return this;
        }

        public Builder setDomainId(String domainId) {
            data.add(DOMAIN_ID, new JsonPrimitive(domainId));
            return this;
        }

        public Builder setGroups(List<String> groups) {
            JsonArray jsonArray = new JsonArray();
            groups.stream().forEach(group -> jsonArray.add(new JsonPrimitive(group)));
            data.add(GROUPS, jsonArray);
            return this;
        }

        public TokenInfo build() {
            return new TokenInfo(data);
        }
    }
}
