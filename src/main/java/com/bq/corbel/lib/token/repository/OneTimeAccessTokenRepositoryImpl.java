package com.bq.corbel.lib.token.repository;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.bq.corbel.lib.token.model.OneTimeAccessToken;

/**
 * @author Alberto J. Rubio
 */
public class OneTimeAccessTokenRepositoryImpl implements CustomOneTimeAccessTokenRepository {

    private static final String TAGS = "tags";

    private final MongoOperations mongoOperations;

    @Autowired
    public OneTimeAccessTokenRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public OneTimeAccessToken findAndRemove(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        return mongoOperations.findAndRemove(query, OneTimeAccessToken.class);
    }

    @Override
    public void deleteByTags(String... tags) {
        if (ArrayUtils.isNotEmpty(tags)) {
            Query query = Query.query(Criteria.where(TAGS).all((Object[]) tags));
            mongoOperations.remove(query, OneTimeAccessToken.class);
        }
    }
}
