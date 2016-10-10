package com.demo.limits.servicessupport.localization;

import com.demo.limits.common.config.CommonConfig;
import com.demo.limits.common.configuration.ConfigurationHelper;
import com.demo.limits.idm.config.IDMConfig;
import com.demo.limits.model.localization.DataLabel;
import com.demo.limits.repository.config.HikariCPDataSourceConfig;
import com.demo.limits.repository.config.RepositoryConfig;
import com.demo.limits.repository.exceptions.EntityNotFoundException;
import com.demo.limits.servicessupport.config.ServicesSupportConfig;
import com.demo.limits.servicessupport.config.TestServicesSupportConfig;
import com.demo.limits.servicessupport.localization.api.DataLabelOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
@ContextConfiguration(classes = {
        HikariCPDataSourceConfig.class,
        RepositoryConfig.class,
        CommonConfig.class,
        IDMConfig.class,
        ServicesSupportConfig.class,
        TestServicesSupportConfig.class
})
public class LocalizationTest extends AbstractTestNGSpringContextTests {

    @Inject
    @Named("ovDataLabelTemplate")
    DataLabelOperations dataLabelOperations;

    @BeforeClass
    public void setup(){
        ConfigurationHelper.defaultContext();
    }

    @Test(expectedExceptions = {EntityNotFoundException.class})
    public void readLabel(){
        final DataLabel dim1Label = dataLabelOperations.get("DIM1", 44L);
        final String dim1LabelS = dataLabelOperations.getLabel("DIM1", 44L, null);
        System.out.println("Check if two queries fired");
    }
}
