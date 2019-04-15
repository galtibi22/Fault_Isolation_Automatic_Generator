package org.afeka.fi.backend.exception;

import org.afeka.fi.backend.pojo.http.ResponseError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice



public class RestExceptionHendler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ResponseError> handleEntityNotFound(ResourceNotFoundException ex, ServletWebRequest request) {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseError.builder().setError(HttpStatus.NOT_FOUND).setMessage(ex.getMessage()).timeStampNow().setReason(ex).setUrl(request.getRequest().getRequestURI()));
    }

}



