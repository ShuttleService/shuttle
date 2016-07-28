package com.real.apps.shuttle.repository;

import com.mongodb.*;
import com.real.apps.shuttle.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;
import java.util.List;

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
    @Value("#{environment.OPENSHIFT_MONGODB_DB_USERNAME}")
    private String username;
    @Value("#{environment.OPENSHIFT_MONGODB_DB_PASSWORD}")
    private String password;

    @Override
    protected String getDatabaseName() {
        return "shuttle";
    }

    @Override
    public Mongo mongo() throws Exception {
        //TODO: test this setup
        String host = this.host != null ? this.host : "localhost";
        int port = this.port != null ? this.port : 27017;
        List<MongoCredential> mongoCredentialList = Arrays.asList(mongoCredential());
        ServerAddress serverAddress = new ServerAddress(host, port);
        MongoClient client = new MongoClient(serverAddress, mongoCredentialList);

        client.setWriteConcern(WriteConcern.SAFE);
        client.setReadPreference(ReadPreference.nearest());

        return client;
    }

    @Override
    public String getMappingBasePackage() {
        return User.class.getPackage().getName();
    }


    public MongoCredential mongoCredential() {
        //TODO test this method
        String username = this.username != null ? this.username : "";
        String password = this.password != null ? this.password : "";
        return MongoCredential.createCredential(username, getDatabaseName(), password.toCharArray());
    }
}