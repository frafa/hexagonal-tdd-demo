package it.ff.hexagonal.tdd.histexrate.codeimpl.adapters.api.exception;

import it.ff.hexagonal.tdd.histexrate.codegen.model.ResponseError;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.exception.BadRequestException;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.exception.GenericServiceException;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.exception.NotFoundException;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.exception.ServiceUnavailableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;


/**
 * Excption Handler
 */
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    /**
     * Handle all the "Throwable" exception not managed directly
     * Actualy manage GenericServiceException and return code 500 (INTERNAL SERVER ERROR)
     * for the other exception the parent method is called
     *
     * @param ex      exception
     * @param request web request
     * @return response object with the error information
     */
    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) throws Exception {
        if (ex instanceof GenericServiceException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, ResponseErrorFactory.create("500", "Internal error"), new HttpHeaders(), status, request);
        }

        return super.handleException(ex, request);
    }

    /**
     * Handle NotFoundException
     *
     * @param ex      exception
     * @param request web request
     * @return response object with the error information, return code 404
     */
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNoHandlerFoundException(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return handleExceptionInternal(ex, ResponseErrorFactory.create("404", "Object not found."), new HttpHeaders(), status, request);
    }

    /**
     * Handle Service Unavailable exception
     *
     * @param ex      exception
     * @param request web request
     * @return response object with the error information, return code 503
     */
    @ExceptionHandler({ServiceUnavailableException.class})
    public ResponseEntity<Object> handleServiceUnavailableException(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        return handleExceptionInternal(ex, ResponseErrorFactory.create("503", "Service unavilable"), new HttpHeaders(), status, request);
    }

    /**
     * Handle Bad Request exception
     *
     * @param ex      exception
     * @param request web request
     * @return response object with the error information, return code 400
     */
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(ex, ResponseErrorFactory.create("400", "Bad Request"), new HttpHeaders(), status, request);
    }

    /**
     * Build the response object
     */
    @Override
    public ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status,
                                                          WebRequest request) {
        if (body == null) {
            ResponseError responseError = new ResponseError();
            responseError.setCode(status.toString());
            responseError.setDescription(status.getReasonPhrase());
            body = responseError;
        }

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
