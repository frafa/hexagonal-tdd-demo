package it.ff.hexagonal.tdd.histexrate.codeimpl.application.port;

import it.ff.hexagonal.tdd.histexrate.codegen.model.ExchangeRate;

import java.time.LocalDate;
import java.util.List;

public interface HistoricalExchangeRatesUseCase {

    List<ExchangeRate> getHistoricExchangeRate(Integer offset, Integer limit, String currency, LocalDate date);

    ExchangeRate insertExchangeRate(ExchangeRate ExchangeRate);

    int updateExchangeRate(ExchangeRate ExchangeRate);

    void deleteExchangeRate(String currency, LocalDate date);
}
