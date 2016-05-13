package com.bq.corbel.lib.token.repository;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Arrays;

import com.bq.corbel.lib.token.model.OneTimeAccessToken;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

/**
 * @author Alexander De Leon
 *
 */
public class OneTimeAccessTokenRepositoryImplTest {

	private OneTimeAccessTokenRepositoryImpl repository;
	private MongoOperations mongoOperationsMock;

	@Before
	public void setup() {
		mongoOperationsMock = mock(MongoOperations.class);
		repository = new OneTimeAccessTokenRepositoryImpl(mongoOperationsMock);
	}

	@Test
	public void testDeleteByTags() {
		String tag1 = "tag1";
		String tag2 = "tag2";
		repository.deleteByTags(tag1, tag2);
		ArgumentCaptor<Query> queryCapturer = ArgumentCaptor.forClass(Query.class);
		verify(mongoOperationsMock).remove(queryCapturer.capture(), Mockito.eq(OneTimeAccessToken.class));
		DBObject tags = (DBObject) queryCapturer.getValue().getQueryObject().get("tags");
		BasicDBList expectedTagList = new BasicDBList();
		expectedTagList.addAll(Arrays.asList(tag1, tag2));
		assertThat(tags.get("$all")).isEqualTo(expectedTagList);
	}

	@Test
	public void testDeleteByTagsEmpty() {
		repository.deleteByTags();
		verifyZeroInteractions(mongoOperationsMock);
	}
}
