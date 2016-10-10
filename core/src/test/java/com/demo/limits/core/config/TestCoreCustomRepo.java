package com.demo.limits.core.config;

import com.demo.limits.repository.config.RepositoryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by lnv.
 */
@Configuration
@PropertySources({
        @PropertySource(value = "classpath:hsql-db-application-core-test.properties"),
        @PropertySource(value = "file:${CONF_DIR}/application.properties", ignoreResourceNotFound = true)
})
@EnableTransactionManagement
@EnableJpaRepositories("com.demo.limits.repository")
public class TestCoreCustomRepo extends RepositoryConfig {
    @Value("classpath:hsqldb-data.sql")
    private Resource dataScript;

    @Value("classpath:data.sql")
    private Resource baseDataScript;

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    protected DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setSeparator("/;");
        populator.addScript(baseDataScript);
        populator.addScript(dataScript);
        return populator;
    }
}
