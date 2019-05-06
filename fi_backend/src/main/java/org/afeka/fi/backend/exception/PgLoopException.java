package org.afeka.fi.backend.exception;

public class PgLoopException extends Exception {
    int number;
    public PgLoopException(int number) {
        super();
        this.number=number;
    }
    public int getNumber(){
        return number;
    }
}
