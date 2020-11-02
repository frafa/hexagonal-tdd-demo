package it.ff.hexagonal.tdd.histexrate.codeimpl.adapters.persistence;

import it.ff.hexagonal.tdd.histexrate.codegen.model.ExchangeRate;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.port.HistoricalExchangeRatesPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class HistoricalExchangeRatesRepository implements HistoricalExchangeRatesPort {

    @Autowired
    private HistExRateRepository repository;

    @Override
    public List<ExchangeRate> findAll(Integer offset, Integer limit) {
        Pageable p = PageRequest.of(offset, limit);
        return mappertToExchangeRate(repository.findAll(p));
    }

    @Override
    public List<ExchangeRate> findAllByIdentity_idCurrency(String currency, Integer offset, Integer limit) {
        Pageable p = PageRequest.of(offset, limit);
        return mappertToExchangeRate(repository.findAllByIdentity_idCurrencyIgnoreCase(currency, p));
    }

    @Override
    public List<ExchangeRate> findAllByIdentity_idDate(LocalDate date, Integer offset, Integer limit) {
        Pageable p = PageRequest.of(offset, limit);
        return mappertToExchangeRate(repository.findAllByIdentity_idDate(date, p));
    }

    @Override
    public List<ExchangeRate> findAllByIdentity_idCurrencyAndIdentity_idDate(String currency, LocalDate date, Integer offset, Integer limit) {
        Pageable p = PageRequest.of(offset, limit);
        return mappertToExchangeRate(repository.findAllByIdentity_idCurrencyAndIdentity_idDate(currency, date, p));
    }

    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) {
        return mapperToExchangeRate(repository.save(mapperToHistExRate(exchangeRate)));
    }

    @Override
    public int updateHistExRate(ExchangeRate exchangeRate) {
        return repository.updateHistExRate(mapperToHistExRate(exchangeRate));
    }

    @Override
    public void deleteHistExRate(String currency, LocalDate data) {
        repository.deleteById(new HistExRateIdentity(currency, data));
    }

    private ExchangeRate mapperToExchangeRate(HistExRate histExRate) {
        ExchangeRate cs = null;
        if (histExRate != null && histExRate.getIdentity() != null && histExRate.getIdentity().getIdCurrency() != null) {
            cs = new ExchangeRate();
            cs.setRate(histExRate.getRate());
            cs.setDate(histExRate.getIdentity().getIdDate());
            cs.setCurrency(histExRate.getIdentity().getIdCurrency());
        }
        return cs;
    }

    private List<ExchangeRate> mappertToExchangeRate(Iterable<HistExRate> i) {
        List<ExchangeRate> res = new ArrayList<>();

        for (HistExRate histExRate : i) {
            res.add(mapperToExchangeRate(histExRate));
        }

        return res;
    }

    private HistExRate mapperToHistExRate(ExchangeRate cs) {
        HistExRate e = new HistExRate();
        e.setRate(cs.getRate());
        HistExRateIdentity identity = new HistExRateIdentity(cs.getCurrency(), cs.getDate());
        e.setIdentity(identity);
        return e;
    }
}
