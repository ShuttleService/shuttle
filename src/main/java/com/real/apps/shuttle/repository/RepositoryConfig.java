package com.real.apps.shuttle.repository;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.real.apps.shuttle.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("#{environment.OPENSHIFT_MONGODB_DB_HOST}")
    private String host;
    @Value("#{environment.OPENSHIFT_MONGODB_DB_PORT}")
    private Integer port;

    @Override
    protected String getDatabaseName() {

        return "shuttle";
    }

    @Override
    public Mongo mongo() throws Exception {
        //TODO: test this setup
        String host = this.host != null ? this.host : "localhost";
        int port = this.port != null ? this.port : 27017;

        MongoClient client = new MongoClient(host, port);

        client.setWriteConcern(WriteConcern.SAFE);
        client.setReadPreference(ReadPreference.nearest());
        return client;
    }

    @Override
    public String getMappingBasePackage() {
        return User.class.getPackage().getName();
    }

    @Override

    public UserCredentials getUserCredentials() {
        return new UserCredentials("", "");
    }
}