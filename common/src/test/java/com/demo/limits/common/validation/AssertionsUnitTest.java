package com.demo.limits.common.validation;

import com.demo.limits.common.config.CommonConfig;
import com.demo.limits.common.config.TestCommonConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;

/**
 * Created by lnv.
 */
@ContextConfiguration(classes = {
        CommonConfig.class,
        TestCommonConfig.class
})
public class AssertionsUnitTest extends AbstractTestNGSpringContextTests {
    @Inject
    Assertions assertions;

    //@Test(expectedExceptions = {ValidationException.class})
    public void testValidation(){
        TestModel testModel = new TestModel();
        testModel.setName("");
        testModel.setDescription("");
        testModel.setUuid(null);
        assertions.validate(testModel);
    }

    @Test
    public void testValidationCheckViolationsCount(){
        TestModel testModel = new TestModel();
        testModel.setName(null);
        testModel.setDescription("");
        testModel.setUuid(null);
        try{
            assertions.validate(testModel);
        }
        catch(BaseValidationException bve){
            System.out.println("I have error");
            assert bve.getViolations().size() > 0;
        }
    }
}
