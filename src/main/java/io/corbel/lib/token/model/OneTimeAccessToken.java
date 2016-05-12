package com.bq.corbel.lib.token.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;

/**
 * @author Alberto J. Rubio
 * 
 */
public class OneTimeAccessToken {

	@Id
	private final String id;
	private final Date expireAt;
	private final List<String> tags;

	public OneTimeAccessToken(String id, Date expireAt, List<String> tags) {
		this.id = id;
		this.expireAt = expireAt;
		this.tags = isEmptyList(tags) ? null : tags;
	}

	private boolean isEmptyList(List<String> list) {
		return list == null || list.size() == 0;
	}

	public String getId() {
		return id;
	}

	public Date getExpireAt() {
		return expireAt;
	}

	public List<String> getTags() {
		return tags;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, expireAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof OneTimeAccessToken)) {
			return false;
		}
		OneTimeAccessToken that = (OneTimeAccessToken) obj;
		return Objects.equals(this.id, that.id) && Objects.equals(this.expireAt, that.expireAt);
	}

}
