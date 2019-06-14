package com.ming.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DBConfig {

    public static final String DB_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "root";
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/Cloud?useUnicode=true&characterEncoding=utf-8&useSSL=false&createDatabaseIfNotExist=true";
    public static final String JDBC_URL_A = "jdbc:mysql://localhost:3306/Cloud1?useUnicode=true&characterEncoding=utf-8&useSSL=false&createDatabaseIfNotExist=true";
    public static final String JDBC_URL_B = "jdbc:mysql://localhost:3306/Cloud2?useUnicode=true&characterEncoding=utf-8&useSSL=false&createDatabaseIfNotExist=true";
    public static final int MAX_POOL_SIZE = 10;

    @Profile("peer1")
    @Bean
    public HikariDataSource peer1DataSource(){
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(DB_DRIVER_CLASS_NAME);
        ds.setJdbcUrl(JDBC_URL);
        ds.setUsername(DB_USERNAME);
        ds.setPassword(DB_PASSWORD);
        ds.setMaximumPoolSize(MAX_POOL_SIZE);
        return ds;
    }

    @Profile("peer2")
    @Bean
    public HikariDataSource peer2DataSource(){
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(DB_DRIVER_CLASS_NAME);
        ds.setJdbcUrl(JDBC_URL);
        ds.setUsername(DB_USERNAME);
        ds.setPassword(DB_PASSWORD);
        ds.setMaximumPoolSize(MAX_POOL_SIZE);
        return ds;
    }
}
