package org.afeka.fi.backend.common;

import org.afeka.fi.backend.Application;
import org.afeka.fi.backend.common.FiLogger;
import org.afeka.fi.backend.common.FiProperties;
import org.afeka.fi.backend.pojo.internal.ENV;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.xml.bind.JAXBException;
import java.io.IOException;
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class FiCommonTest {

    protected FiLogger logger = new FiLogger();

    @Before
    public void init() throws Exception {
        logger.info("Start init method from FiCommonTest");
        //FiProperties.init();
    }


}



