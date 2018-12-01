package org.afeka.fi.tests.units;

import org.afeka.fi.backend.common.FiLogger;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class Log4j2Tests {

    Logger logger = FiLogger.getLogger(this.getClass());

    @Test
    public void logLevelsPrintTest(){
        logger.trace("Trace level");
        logger.debug("Debug level");
        logger.info("Info level");
        logger.error("Error level");
        logger.fatal("Fatal level");
    }
}
