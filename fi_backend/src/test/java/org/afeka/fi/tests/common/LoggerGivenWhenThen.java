package org.afeka.fi.tests.common;

import org.afeka.fi.backend.common.FiCommon;

public abstract class LoggerGivenWhenThen extends FiCommon implements GivenWhenThen{


    @Override
    public void given(String msg) {
        logger.info("Given "+msg);
    }

    @Override
    public void when(String msg) {
        logger.info("When "+msg);

    }

    @Override
    public void then(String msg) {
        logger.info("Then "+msg);

    }

    @Override
    public void and(String msg) {
        logger.info("And "+msg);

    }
}
