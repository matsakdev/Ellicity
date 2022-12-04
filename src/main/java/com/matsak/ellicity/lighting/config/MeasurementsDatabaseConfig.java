package com.matsak.ellicity.lighting.config;

import com.mongodb.client.*;
import org.bson.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.config.*;
import org.springframework.data.mongodb.repository.config.*;

import java.util.function.*;

//@EnableJpaRepositories(basePackages = "com.matsak.ellicity.lighting.repository.measurements",
//        entityManagerFactoryRef = "measurementsEntityManagerFactory", transactionManagerRef = "measurementsTransactionManager")
//@Configuration
//public class MeasurementsDatabaseConfig {
//
//    @Bean
//    @ConfigurationProperties("spring.measurements.datasource")
//    public DataSourceProperties measurementsDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    public DataSource measurementsDataSource(
//            @Qualifier("measurementsDataSourceProperties")  DataSourceProperties measurementsDataSourceProperties) {
//        return measurementsDataSourceProperties.initializeDataSourceBuilder()
//                .type(HikariDataSource.class)
//                .build();
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean measurementsEntityManagerFactory(
//            @Qualifier("measurementsDataSource") DataSource measurementsDataSource,
//            EntityManagerFactoryBuilder builder) {
//        return builder.dataSource(measurementsDataSource)
//                .packages(Measurement.class)
//                .persistenceUnit("measurements")
//                .build();
//    }
//
//    @Bean
//    public PlatformTransactionManager measurementsTransactionManager(
//            @Qualifier("measurementsEntityManagerFactory")
//            LocalContainerEntityManagerFactoryBean measurementsEntityManagerFactory) {
//        return new JpaTransactionManager(measurementsEntityManagerFactory.getObject());
//
//    }
//}

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
        ListDatabasesIterable<Document> databases = client.listDatabases();
        client.listDatabaseNames().forEach((Consumer<String>) System.out::println);
        client.listDatabases().forEach((Consumer<Document>) System.out::println);
        return client;
    }
}
