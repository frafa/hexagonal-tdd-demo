package it.ff.hexagonal.tdd.histexrate.codeimpl.application.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Exception e) {
        super(e);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
