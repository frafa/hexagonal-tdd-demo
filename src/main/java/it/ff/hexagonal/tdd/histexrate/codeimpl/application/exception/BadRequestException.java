package it.ff.hexagonal.tdd.histexrate.codeimpl.application.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
