package org.afeka.fi.backend.exception;

public class DeleteNotSuccessException extends Exception {

    public DeleteNotSuccessException(){
        super();
    }
    public DeleteNotSuccessException(String msg){
        super(msg);
    }
}
