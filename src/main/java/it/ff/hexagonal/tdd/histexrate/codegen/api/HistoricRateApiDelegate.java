package it.ff.hexagonal.tdd.histexrate.codegen.api;

import it.ff.hexagonal.tdd.histexrate.codegen.model.ExchangeRate;
import it.ff.hexagonal.tdd.histexrate.codegen.model.HistoricRatesList;

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
 * A delegate to be called by the {@link HistoricRateApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T22:05:16.885+02:00[Europe/Rome]")
public interface HistoricRateApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * DELETE /historic-rate : Delete an historical rate record
     *
     * @param currency Currency code (ISO 4217) of the exchange rate to delete. (optional)
     * @param date     Date (ISO format) of the exchange rate to delete. (optional)
     * @return Record delete. (status code 204)
     * or Bad request (status code 400)
     * or Not found (status code 404)
     * or Internal Server Error (status code 500)
     * or Service Unavailable (status code 503)
     * @see HistoricRateApi#deleteExchangeRate
     */
    default ResponseEntity<Void> deleteExchangeRate(String currency,
                                                    LocalDate date) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /historic-rate : Retrieve historic exchange rates.
     * Retrieve a list oh historic rate according to parameters. The number of elements returned depends on page and size parameter.
     *
     * @param currency currency code ISO 4217 to be retrieved (optional)
     * @param date     date to be retrieved (optional)
     * @param page     the page number to request. the page number is 0-based (default 0). (optional)
     * @param size     results per page the default is 10 (optional)
     * @return Return a list of historical exchange rates. (status code 200)
     * or Bad request (status code 400)
     * or Unauthorized (status code 401)
     * or Internal Server Error (status code 500)
     * or Service Unavailable (status code 503)
     * @see HistoricRateApi#getHistoricRates
     */
    default ResponseEntity<HistoricRatesList> getHistoricRates(String currency,
                                                               LocalDate date,
                                                               Integer page,
                                                               Integer size) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"historic_rates\" : [ { \"rate\" : 48.09, \"currency\" : \"CHF\", \"date\" : \"2018-01-17\" }, { \"rate\" : 64.14, \"currency\" : \"USD\", \"date\" : \"2018-01-17\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /historic-rate : Insert or update an historical rate.
     * Insert new historical rate record, if already exists a record with the same currency and date it will be update.
     *
     * @param exchangeRate Il nuovo elemento dello storico cambi da creare (required)
     * @return Successful response. (status code 201)
     * or Bad request (status code 400)
     * or Internal Server Error (status code 500)
     * or Service Unavailable (status code 503)
     * @see HistoricRateApi#insertExchangeRate
     */
    default ResponseEntity<ExchangeRate> insertExchangeRate(ExchangeRate exchangeRate) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"rate\" : 23.06, \"currency\" : \"CHF\", \"date\" : \"2018-01-17\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PUT /historic-rate : Update an historical exchange rate
     * Update historical exchange rate record.
     *
     * @param exchangeRate Informazioni utili ad aggiornare il record (required)
     * @return Update successful. (status code 204)
     * or Bad request (status code 400)
     * or Not found (status code 404)
     * or Internal Server Error (status code 500)
     * or Service Unavailable (status code 503)
     * @see HistoricRateApi#updateExchangeRate
     */
    default ResponseEntity<Void> updateExchangeRate(ExchangeRate exchangeRate) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
