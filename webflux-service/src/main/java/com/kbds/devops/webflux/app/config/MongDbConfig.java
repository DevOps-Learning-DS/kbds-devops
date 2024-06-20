package com.kbds.devops.webflux.app.config;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
public class MongDbConfig { // extends AbstractReactiveMongoConfiguration {

//    @Bean
//    public MongoClient mongoClient() {
//        return MongoClients.create();
//    }
//
//    @Override
//    protected String getDatabaseName() {
//        return "reactive";
//    }
}