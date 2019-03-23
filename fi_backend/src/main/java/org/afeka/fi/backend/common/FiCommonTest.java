package org.afeka.fi.backend.common;

import org.afeka.fi.backend.common.FiLogger;
import org.afeka.fi.backend.common.FiProperties;
import org.afeka.fi.backend.pojo.internal.ENV;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class FiCommonTest {

    protected FiLogger logger = new FiLogger();

    @Before
    public void init() throws Exception {
        logger.info("Start init method from FiCommonTest");
        FiProperties.init();
    }


}



