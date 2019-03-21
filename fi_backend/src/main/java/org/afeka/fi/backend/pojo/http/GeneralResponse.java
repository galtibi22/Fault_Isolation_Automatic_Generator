package org.afeka.fi.backend.pojo.http;

public class GeneralResponse {
    private String message;

    public GeneralResponse(String message){
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
