package it.ff.hexagonal.tdd.histexrate.codegen.api;

import java.math.BigDecimal;

import it.ff.hexagonal.tdd.histexrate.codegen.model.ConvertedAmount;

import java.time.LocalDate;

import it.ff.hexagonal.tdd.histexrate.codegen.model.ResponseError;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A delegate to be called by the {@link ConvertToApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T22:05:16.885+02:00[Europe/Rome]")
public interface ConvertToApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /convert-to : Convert amount from base currency to another
     *
     * @param currencyFrom Currency (ISO 4217 code) you want to convert from. (required)
     * @param currencyTo   Destination currency (ISO 4217 code) you want to convert. (required)
     * @param amount       Amount to be converted (required)
     * @param date         Date (ISO format) used to retrieve historic exchange rate (optional)
     * @return Return the amount converted from base currency to another, all the eschange rate used will be returned. (status code 200)
     * or Bad request (status code 400)
     * or Not found (status code 404)
     * or Internal Server Error (status code 500)
     * or Service Unavailable (status code 503)
     * @see ConvertToApi#getConvertedAmount
     */
    default ResponseEntity<ConvertedAmount> getConvertedAmount(String currencyFrom,
                                                               String currencyTo,
                                                               BigDecimal amount,
                                                               LocalDate date) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"currency\" : \"USD\", \"exchange_rates\" : [ { \"rate\" : 86.1, \"currency\" : \"CHF\", \"date\" : \"2018-01-17\" }, { \"rate\" : 82.25, \"currency\" : \"USD\", \"date\" : \"2018-01-17\" } ], \"amount\" : 65.48 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
