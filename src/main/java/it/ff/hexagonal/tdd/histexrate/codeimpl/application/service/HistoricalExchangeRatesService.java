package it.ff.hexagonal.tdd.histexrate.codeimpl.application.service;

import it.ff.hexagonal.tdd.histexrate.codegen.model.ExchangeRate;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.exception.ServiceUnavailableException;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.port.HistoricalExchangeRatesPort;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.port.HistoricalExchangeRatesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Component
public class HistoricalExchangeRatesService implements HistoricalExchangeRatesUseCase {

    @Autowired
    private HistoricalExchangeRatesPort historicalExchangeRatesPort;

    @Override
    public List<ExchangeRate> getHistoricExchangeRate(Integer offset, Integer limit, String currency, LocalDate date) {
        int off = (offset == null || offset <= 0) ? 0 : offset - 1;
        int lim = (limit == null || limit <= 0) ? 10 : limit;

        List<ExchangeRate> l;

        try {
            if (StringUtils.isEmpty(currency) && date == null) {
                l = historicalExchangeRatesPort.findAll(off, lim);
            } else if (!StringUtils.isEmpty(currency) && date == null) {
                l = historicalExchangeRatesPort.findAllByIdentity_idCurrency(currency, off, lim);
            } else if (StringUtils.isEmpty(currency) && date != null) {
                l = historicalExchangeRatesPort.findAllByIdentity_idDate(date, off, lim);
            } else {
                l = historicalExchangeRatesPort.findAllByIdentity_idCurrencyAndIdentity_idDate(currency, date, off, lim);
            }

            return l;
        } catch (Exception e) {
            throw new ServiceUnavailableException(e);
        }
    }

    @Override
    public ExchangeRate insertExchangeRate(ExchangeRate exchangeRate) {
        try {
            return historicalExchangeRatesPort.save(exchangeRate);
        } catch (Exception e) {
            throw new ServiceUnavailableException("Errore del servizio");
        }
    }

    @Override
    public int updateExchangeRate(ExchangeRate exchangeRate) {
        try {
            return historicalExchangeRatesPort.updateHistExRate(exchangeRate);
        } catch (Exception e) {
            throw new ServiceUnavailableException("Errore del servizio");
        }
    }

    @Override
    public void deleteExchangeRate(String currency, LocalDate date) {
        try {
            historicalExchangeRatesPort.deleteHistExRate(currency, date);
        } catch (EmptyResultDataAccessException x) {
            throw x;
        } catch (Exception e) {
            throw new ServiceUnavailableException(e);
        }
    }
}
