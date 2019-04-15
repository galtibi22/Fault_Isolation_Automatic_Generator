package org.afeka.fi.backend.pojo.http;


import org.springframework.http.HttpStatus;

import java.util.Date;

public class ResponseError {
    private Date timestamp;
    private String error;
    private String message;
    private String url;
    private Exception reason;

    public ResponseError(Date timestamp,String error, String message, String url, Exception reason) {
        this.timestamp = timestamp;
        this.error = error;
        this.message = message;
        this.url = url;
        this.reason = reason;
    }

    public ResponseError(){

    }
    public Date getTimestamp() {
        return timestamp;
    }

    public ResponseError timeStampNow(){
        return setTimestamp(new Date());
    }

    public ResponseError setError(HttpStatus status){
        this.error=status.getReasonPhrase();
        return this;
    }

    public ResponseError setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }


    public String getError() {
        return error;
    }

 /*   public ResponseError setError(String error) {
        this.error = error;
        return this;
    }*/

    public String getMessage() {
        return message;
    }

    public ResponseError setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ResponseError setUrl(String url) {
        this.url = url;
        return this;
    }

    public Exception getReason() {
        return reason;
    }

    public ResponseError setReason(Exception reason) {
        this.reason = reason;
        return this;
    }

    public static ResponseError builder(){
        return new ResponseError();
    }




}
