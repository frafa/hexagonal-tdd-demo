package it.ff.hexagonal.tdd.histexrate.codeimpl.application.exception;

public class GenericServiceException extends RuntimeException {

    public GenericServiceException(Exception e) {
        super(e);
    }
}
