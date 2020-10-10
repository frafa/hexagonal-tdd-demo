package it.ff.hexagonal.tdd.histexrate.codeimpl.adapters.api.exception;

import it.ff.hexagonal.tdd.histexrate.codegen.model.ResponseError;

public class ResponseErrorFactory {
    private ResponseErrorFactory() {
    }

    public static ResponseError create(String code, String description) {
        ResponseError responseError = new ResponseError();
        responseError.setCode(code);
        responseError.setDescription(description);

        return responseError;
    }
}
