package org.afeka.fi.backend.exception;

import org.afeka.fi.backend.common.FiLogger;
import org.afeka.fi.backend.pojo.http.ResponseError;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHendler extends ResponseEntityExceptionHandler{
    Logger logger= FiLogger.getLogger(getClass().getName());
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ResponseError> handleEntityNotFound(ResourceNotFoundException ex, ServletWebRequest request) {
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseError.builder().setError(HttpStatus.NOT_FOUND).setMessage(ex.getMessage()).timeStampNow().setReason(ex).setUrl(request.getRequest().getRequestURI()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ResponseError> illegalArgumentException(IllegalArgumentException ex, ServletWebRequest request) {
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseError.builder().setError(HttpStatus.BAD_REQUEST).setMessage(ex.getMessage()).timeStampNow().setReason(ex).setUrl(request.getRequest().getRequestURI()));
    }

    @ExceptionHandler(FileNotSupportExption.class)
    protected ResponseEntity<ResponseError> fileNotSupportExption(FileNotSupportExption ex, ServletWebRequest request) {
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseError.builder().setError(HttpStatus.BAD_REQUEST).setMessage(ex.getMessage()).timeStampNow().setReason(ex).setUrl(request.getRequest().getRequestURI()));
    }
    @ExceptionHandler(FiGenratorException.class)
    protected ResponseEntity<ResponseError> fiGenratorException(FiGenratorException ex, ServletWebRequest request) {
        return  ResponseEntity.status(HttpStatus.METHOD_FAILURE).body(
                ResponseError.builder().setError(HttpStatus.METHOD_FAILURE).setMessage(ex.getMessage()).timeStampNow().setReason(ex).setUrl(request.getRequest().getRequestURI()));
    }
    @ExceptionHandler(IOException.class)
    protected ResponseEntity<ResponseError> iOException(IOException ex, ServletWebRequest request) {
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseError.builder().setError(HttpStatus.BAD_REQUEST).setMessage(ex.getMessage()).timeStampNow().setReason(ex).setUrl(request.getRequest().getRequestURI()));
    }
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseError> exception(Exception ex, ServletWebRequest request) {
        logger.error(ex);
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ResponseError.builder().setError(HttpStatus.INTERNAL_SERVER_ERROR).setMessage(ex.getMessage()).timeStampNow().setReason(ex).setUrl(request.getRequest().getRequestURI()));
    }
    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<ResponseError> emptyResultDataAccessException(EmptyResultDataAccessException ex, ServletWebRequest request) {
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ResponseError.builder().setError(HttpStatus.INTERNAL_SERVER_ERROR).setMessage(ex.getMessage()).timeStampNow().setReason(ex).setUrl(request.getRequest().getRequestURI()));
    }
    @ExceptionHandler(DeleteEntityExption.class)
    protected ResponseEntity<ResponseError> deleteEntityExption(EmptyResultDataAccessException ex, ServletWebRequest request) {
        return  ResponseEntity.status(HttpStatus.METHOD_FAILURE).body(
                ResponseError.builder().setError(HttpStatus.METHOD_FAILURE).setMessage(ex.getMessage()).timeStampNow().setReason(ex).setUrl(request.getRequest().getRequestURI()));
    }

}



