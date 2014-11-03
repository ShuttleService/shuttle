package com.real.apps.shuttle.respository;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.real.apps.shuttle.model.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by zorodzayi on 14/10/26.
 */
@Configuration
@EnableMongoRepositories
public class RepositoryConfig extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {

        return "shuttle";
    }

    @Override
    public Mongo mongo() throws Exception {
        MongoClient client = new MongoClient();
        client.setWriteConcern(WriteConcern.SAFE);
        client.setReadPreference(ReadPreference.nearest());

        return client;
    }

    @Override
    public String getMappingBasePackage() {
        return User.class.getPackage().getName();
    }

    @Override
    public UserCredentials getUserCredentials(){
        return new UserCredentials("","");
    }
}