package org.afeka.fi.tests.common;

import org.afeka.fi.backend.Application;
import org.afeka.fi.backend.common.FiLogger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public abstract class FiCommonTest extends LoggerGivenWhenThen {

    protected FiLogger logger = new FiLogger();

    @Before
    public void init() throws Exception {
        logger.info("Start init method from FiCommonTest");
        //FiProperties.init();
    }


}



