package it.ff.hexagonal.tdd.histexrate.codeimpl.application.service;

import it.ff.hexagonal.tdd.histexrate.codegen.model.ExchangeRate;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.exception.ServiceUnavailableException;
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
    private it.ff.hexagonal.tdd.histexrate.codeimpl.application.port.HistoricalExchangeRatesPort HistoricalExchangeRatesPort;

    @Override
    public List<ExchangeRate> getHistoricExchangeRate(Integer offset, Integer limit, String currency, LocalDate date) {
        int off = (offset == null || offset <= 0) ? 0 : offset - 1;
        int lim = (limit == null || limit <= 0) ? 10 : limit;

        List<ExchangeRate> l;

        try {
            if (StringUtils.isEmpty(currency) && date == null) {
                l = HistoricalExchangeRatesPort.findAll(off, lim);
            } else if (!StringUtils.isEmpty(currency) && date == null) {
                l = HistoricalExchangeRatesPort.findAllByIdentity_idCurrency(currency, off, lim);
            } else if (StringUtils.isEmpty(currency) && date != null) {
                l = HistoricalExchangeRatesPort.findAllByIdentity_idDate(date, off, lim);
            } else {
                l = HistoricalExchangeRatesPort.findAllByIdentity_idCurrencyAndIdentity_idDate(currency, date, off, lim);
            }

            return l;
        } catch (Exception e) {
            throw new ServiceUnavailableException(e);
        }
    }

    @Override
    public ExchangeRate insertExchangeRate(ExchangeRate ExchangeRate) {
        try {
            return HistoricalExchangeRatesPort.save(ExchangeRate);
        } catch (Exception e) {
            throw new ServiceUnavailableException("Errore del servizio");
        }
    }

    @Override
    public int updateExchangeRate(ExchangeRate ExchangeRate) {
        try {
            return HistoricalExchangeRatesPort.updateHistExRate(ExchangeRate);
        } catch (Exception e) {
            throw new ServiceUnavailableException("Errore del servizio");
        }
    }

    @Override
    public void deleteExchangeRate(String currency, LocalDate date) {
        try {
            HistoricalExchangeRatesPort.deleteHistExRate(currency, date);
        } catch (EmptyResultDataAccessException x) {
            throw x;
        } catch (Exception e) {
            throw new ServiceUnavailableException(e);
        }
    }
}
