package com.demo.limits.repository.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by lnv.
 */
@Configuration
public class HikariCPDataSourceConfig {
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
    private static final String PROPERTY_NAME_POOL_SIZE = "db.pool.size";
    private static final Integer DEFAULT_POOL_SIZE=15;

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() {

        HikariConfig config = new HikariConfig();
        //TODO: db.datasource.class
        config.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        config.setJdbcUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        config.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        config.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
        Integer poolSize = env.getProperty(PROPERTY_NAME_POOL_SIZE,Integer.class,DEFAULT_POOL_SIZE);
        config.setMaximumPoolSize(poolSize);
        // TODO: Check these options
        //config.addDataSourceProperty("cachePrepStmts", "true");
        //config.addDataSourceProperty("prepStmtCacheSize", "250");
        //config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        //config.addDataSourceProperty("useServerPrepStmts", "true");
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }
}
