package org.afeka.fi.backend.exception;

public class DataFactoryNotFoundException extends Exception {
    public DataFactoryNotFoundException(String message) {
        super(message);
    }

    public DataFactoryNotFoundException(String pattern, String data) {
        super(String.format("Cannot find pattern=$s in data=$s",pattern,data));
    }

}
