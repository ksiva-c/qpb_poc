package com.demo.limits.common.validation;

import com.demo.limits.common.config.CommonConfig;
import com.demo.limits.common.config.TestCommonConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;

/**
 * User: lnv
 * Date: 
 * Time: 
 */


@ContextConfiguration(classes = {
        CommonConfig.class,
        TestCommonConfig.class
})
public class ValidationTest extends AbstractTestNGSpringContextTests {

    @Inject
    ValidationService validationService;

    @Test
    public void testNew(){
        ValidationBean bean = new ValidationBean();

        //Validate id is null
        bean.setId(1l);
        bean.setName("lnv");
        bean.setDescription("lnv");

        try{
            validationService.create(bean);
        }
        catch(TestValidationException be){
            assert (be.getViolations().size() == 1);
        }
    }

    public void testBoth(){

    }

    public void testDefault(){

    }
}
