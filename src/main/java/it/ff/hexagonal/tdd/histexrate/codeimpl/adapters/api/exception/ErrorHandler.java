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
 * Handler delle eccezioni utile a comppilare una response con le informazioni desiderate.
 */
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    /**
     * Gestisce tutte le eccezioni "Throwable" non gestite esplicitamente
     * Attualmente viene gestito nel caso di "GenericServiceException" il return code 500
     * per tutte le altre si rimanda al metodo padre
     *
     * @param ex      eccezione
     * @param request web request
     * @return Oggetto di risposta del servizio rest con le informazioni sull'errore
     */
    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) throws Exception {
        if (ex instanceof GenericServiceException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, ResponseErrorFactory.create("500", "Errore tecnico"), new HttpHeaders(), status, request);
        }

        return super.handleException(ex, request);
    }

    /**
     * Gestisce le eccezzioni di tipo NotFoundException
     *
     * @param ex      eccezione
     * @param request web request
     * @return risposta del servizio con i dati dell'eccezione e return status code 401
     */
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNoHandlerFoundException(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return handleExceptionInternal(ex, ResponseErrorFactory.create("404", "Risorsa non presente."), new HttpHeaders(), status, request);
    }

    /**
     * Gestisce le eccezzioni di tipo Service Unavailable
     *
     * @param ex      eccezione
     * @param request web request
     * @return risposta del servizio con i dati dell'eccezione e return status code 401
     */
    @ExceptionHandler({ServiceUnavailableException.class})
    public ResponseEntity<Object> handleServiceUnavailableException(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        return handleExceptionInternal(ex, ResponseErrorFactory.create("503", "Errore servizio non disponibile"), new HttpHeaders(), status, request);
    }

    /**
     * Gestisce le eccezzioni di tipo Bad Request
     *
     * @param ex      eccezione
     * @param request web request
     * @return risposta del servizio con i dati dell'eccezione e return status code 401
     */
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(ex, ResponseErrorFactory.create("400", "Errore richiesta non valida"), new HttpHeaders(), status, request);
    }

    /**
     * Costruisce per tutte le eccezioni, anche non esplicitamente gestite, un oggetto di risposta adeguato.
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
