package com.demo.limits.repository.base;

import com.demo.limits.common.config.CommonConfig;
import com.demo.limits.common.configuration.ConfigurationHelper;
import com.demo.limits.idm.config.IDMConfig;
import com.demo.limits.repository.config.HikariCPDataSourceConfig;
import com.demo.limits.repository.config.RepositoryConfig;
import com.demo.limits.repository.config.TestRepositoryConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

/**
 * User: lnv
 * Date:
 * Time: 
 */
@ContextConfiguration(classes = {
        HikariCPDataSourceConfig.class,
        RepositoryConfig.class,
        TestRepositoryConfig.class,
        CommonConfig.class,
        IDMConfig.class}
)
public class AbstractTransactionalRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @BeforeSuite
    public void setup(){
        ConfigurationHelper.defaultContext();
    }

}
