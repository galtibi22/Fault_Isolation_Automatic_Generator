package org.afeka.fi.backend.exception;

public class DataNotFoundException extends Exception {
    public DataNotFoundException(String message) {
        super(message);
    }
    public DataNotFoundException(String pattern,String data) {
        super(String.format("Cannot find pattern=$s in data=$s",pattern,data));
    }

}
