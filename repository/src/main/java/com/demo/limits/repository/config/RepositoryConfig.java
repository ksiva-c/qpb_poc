package com.demo.limits.repository.config;


import com.nurkiewicz.jdbcrepository.sql.SqlGenerator;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * User: lnv
 * Date: 
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.demo.limits.repository")
@ComponentScan(basePackages = {"com.demo.limits.repository.utils"})
public class RepositoryConfig {


    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
    private static final String PROPERTY_NAME_GENERATE_DDL = "generate.ddl";
    public static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    public static final String PROPERTY_NAME_DS_INITIALIZER = "ds.initializer";

    @Resource
    private Environment env;

    @Value("classpath:data.sql")
    private org.springframework.core.io.Resource dataScript;

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        // spring sets hibernate.hbm2ddl.auto","update" when generate ddl is true
        jpaVendorAdapter.setGenerateDdl(env.getProperty(PROPERTY_NAME_GENERATE_DDL, Boolean.class, false));
        jpaVendorAdapter.setShowSql(env.getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL, Boolean.class, false));
        jpaVendorAdapter.setDatabasePlatform(env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        return jpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
        lemfb.setDataSource(dataSource);
        lemfb.setJpaVendorAdapter(jpaVendorAdapter);
        // TOD: Need to setup a way to override based on provider if required
        Properties providerProperties = hibProperties();
        lemfb.setJpaProperties(providerProperties);
        String[] packagesToScan = packagesToScan();
        lemfb.setPackagesToScan(packagesToScan);
        return lemfb;
    }

    protected String[] packagesToScan() {
        String pkgToScan = env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN);
        String[] packagesToScan = pkgToScan.split(",");
        return packagesToScan;
    }

    protected Properties hibProperties() {
        Properties properties = new Properties();
        properties.put(
                PROPERTY_NAME_HIBERNATE_FORMAT_SQL,
                env.getProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, Boolean.class, false));
        Boolean generateDDL = env.getProperty(PROPERTY_NAME_GENERATE_DDL, Boolean.class, false);
        if (generateDDL) {
            properties.put("hibernate.hbm2ddl.auto", "create");
        }
        properties.put("hibernate.id.new_generator_mappings", "true");
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }



    @Bean
    public SqlGenerator sqlGenerator() {
        return new SqlGenerator();
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        DataSourceInitializer initializer = createDataSourceInitializer(dataSource);
        return initializer;
    }

    protected DataSourceInitializer createDataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        Boolean dsInitializerEnabled = env.getProperty(PROPERTY_NAME_DS_INITIALIZER, Boolean.class, false);
        initializer.setEnabled(dsInitializerEnabled);
        return initializer;
    }

    protected DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        Boolean dsInitializerEnabled = env.getProperty(PROPERTY_NAME_DS_INITIALIZER, Boolean.class, false);
        if(dsInitializerEnabled && dataScript.exists()) {
            populator.addScript(dataScript);
        }
        return populator;
    }

    @Bean
    @DependsOn(value = "dataSourceInitializer")
    public JdbcOperations jdbcOperations(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
