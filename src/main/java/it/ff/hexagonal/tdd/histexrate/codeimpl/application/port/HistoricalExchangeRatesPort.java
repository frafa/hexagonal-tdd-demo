package it.ff.hexagonal.tdd.histexrate.codeimpl.application.port;


import it.ff.hexagonal.tdd.histexrate.codegen.model.ExchangeRate;

import java.time.LocalDate;
import java.util.List;

public interface HistoricalExchangeRatesPort {
    List<ExchangeRate> findAll(Integer offset, Integer limit);

    List<ExchangeRate> findAllByIdentity_idCurrency(String divisa, Integer offset, Integer limit);

    List<ExchangeRate> findAllByIdentity_idDate(LocalDate dateRate, Integer offset, Integer limit);

    List<ExchangeRate> findAllByIdentity_idCurrencyAndIdentity_idDate(String divisa, LocalDate dateRate, Integer offset, Integer limit);

    ExchangeRate save(ExchangeRate ExchangeRate);

    int updateHistExRate(ExchangeRate ExchangeRate);

    void deleteHistExRate(String divisa, LocalDate data);

//    ExchangeRate findAllByHistExRateIdentity_currencyCodeAndHistExRateIdentity_dateRate(String divisa, LocalDate dateRate);

}
