package com.matsak.ellicity.lighting.config;

import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.entity.sections.Device;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(value = "com.matsak.ellicity.lighting.repository.systeminfo",
    entityManagerFactoryRef = "systemInfoEntityManagerFactory",
    transactionManagerRef = "systemInfoTransactionManager")
public class SystemInfoDatabaseConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.systeminfo.datasource")
    public DataSourceProperties systemInfoDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource systemInfoDataSource(
            @Qualifier("systemInfoDataSourceProperties")  DataSourceProperties systemInfoDataSourceProperties) {
        return systemInfoDataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean systemInfoEntityManagerFactory(
            @Qualifier("systemInfoDataSource") DataSource systemInfoDataSource,
            EntityManagerFactoryBuilder builder) {
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.hbm2ddl.auto", "update");
        return builder.dataSource(systemInfoDataSource)
                .packages(Circuit.class, Device.class, System.class)
                .persistenceUnit("systemInfo")
                .properties(properties)
                .build();
    }


    @Bean
    @Primary
    public PlatformTransactionManager systemInfoTransactionManager(
            @Qualifier("systemInfoEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean systemInfoEntityManagerFactory) {
        return new JpaTransactionManager(systemInfoEntityManagerFactory.getObject());

    }
}
