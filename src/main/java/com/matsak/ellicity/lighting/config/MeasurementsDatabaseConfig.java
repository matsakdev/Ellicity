package com.matsak.ellicity.lighting.config;

import com.mongodb.client.*;
import org.bson.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.config.*;
import org.springframework.data.mongodb.repository.config.*;

import java.util.function.*;
@Configuration
@EnableMongoRepositories(basePackages = "com.matsak.ellicity.lighting.repository.measurements")
@PropertySource("classpath:mongo.properties")
public class MeasurementsDatabaseConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.measurements.datasource.url}")
    private String url;

    @Value("${spring.measurements.datasource.db}")
    private String db;

    @Override
    protected String getDatabaseName() {
        return db;
    }

    @Override
    public MongoClient mongoClient() {
        MongoClient client = MongoClients.create(url);
        System.out.println(url);
        ListDatabasesIterable<Document> databases = client.listDatabases();
        client.listDatabaseNames().forEach((Consumer<String>) System.out::println);
        client.listDatabases().forEach((Consumer<Document>) System.out::println);
        return client;
    }
}
