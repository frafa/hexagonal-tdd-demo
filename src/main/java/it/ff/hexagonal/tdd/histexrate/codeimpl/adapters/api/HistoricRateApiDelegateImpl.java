package it.ff.hexagonal.tdd.histexrate.codeimpl.adapters.api;

import it.ff.hexagonal.tdd.histexrate.codegen.api.HistoricRateApiDelegate;
import it.ff.hexagonal.tdd.histexrate.codegen.model.ExchangeRate;
import it.ff.hexagonal.tdd.histexrate.codegen.model.HistoricRatesList;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.exception.BadRequestException;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.exception.GenericServiceException;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.exception.NotFoundException;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.exception.ServiceUnavailableException;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.port.HistoricalExchangeRatesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Component
public class HistoricRateApiDelegateImpl implements HistoricRateApiDelegate {

    @Autowired
    private HistoricalExchangeRatesUseCase historicalExchangeRatesUseCase;

    @Override
    public ResponseEntity<HistoricRatesList> getHistoricRates(String currency,
                                                              LocalDate date,
                                                              Integer page,
                                                              Integer size) {
        HistoricRatesList response;
        try {
            List<ExchangeRate> l = historicalExchangeRatesUseCase.getHistoricExchangeRate(page, size, currency, date);

            response = new HistoricRatesList();
            response.historicRates(l);
        } catch (ServiceUnavailableException ex) {
            throw ex;
        } catch (Exception e) {
            throw new GenericServiceException(e);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ExchangeRate> insertExchangeRate(ExchangeRate exchangeRate) {
        ExchangeRate response;

        try {
            response = historicalExchangeRatesUseCase.insertExchangeRate(exchangeRate);
        } catch (ServiceUnavailableException ex) {
            throw ex;
        } catch (Exception e) {
            throw new GenericServiceException(e);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> updateExchangeRate(ExchangeRate exchangeRate) {
        try {
            int i = historicalExchangeRatesUseCase.updateExchangeRate(exchangeRate);
            if (i == 0) {
                throw new NotFoundException("Exchange rate not found.");
            }

        } catch (ServiceUnavailableException | NotFoundException ex) {
            throw ex;
        } catch (Exception e) {
            throw new GenericServiceException(e);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteExchangeRate(String currency, LocalDate date) {
        if (StringUtils.isEmpty(currency) || date == null) {
            throw new BadRequestException("Currency and date could not be null.");
        }

        try {
            historicalExchangeRatesUseCase.deleteExchangeRate(currency, date);
        } catch (EmptyResultDataAccessException x) {
            throw new NotFoundException(x);
        } catch (ServiceUnavailableException ex) {
            throw ex;
        } catch (Exception e) {
            throw new GenericServiceException(e);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
