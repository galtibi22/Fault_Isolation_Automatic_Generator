package org.afeka.fi.backend.common;

import org.apache.logging.log4j.LogManager;

import java.util.Arrays;

public class FiLogger extends LogManager {


    public void info(String msg) {
        getLogger(getCallerClass()).info(msg);
    }

    public void debug(String msg) {
        getLogger(getCallerClass()).debug(msg);
    }

    public void error(String msg) {
        getLogger(getCallerClass()).error(msg);
    }

    public void error(Exception e) {
        getLogger(getCallerClass()).error(e.getMessage(),e);
    }

    public void start(String method) {
        getLogger(getCallerClass()).info("Start " + method + " method");
    }

    public void finish(String method) {
        getLogger(getCallerClass()).info("Finish " + method + " method");
    }

    public void called(String method, String with,Object... data) {
        if (!with.equals(""))
            with="with "+with;
        getLogger(getCallerClass()).info(method + " called " + with+" "+ Arrays.toString(data));
    }


    private String getCallerClass() {
        return new Exception().getStackTrace()[2].getClassName();
    }
}
