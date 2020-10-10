package it.ff.hexagonal.tdd.histexrate;

import it.ff.hexagonal.tdd.histexrate.codegen.model.ExchangeRate;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.exception.ServiceUnavailableException;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.port.HistoricalExchangeRatesPort;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.service.HistoricalExchangeRatesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class})
public class HistoricalExchangeRatesServiceTest {

    @InjectMocks
    private HistoricalExchangeRatesService HistoricalExchangeRatesService;

    @MockBean
    private HistoricalExchangeRatesPort HistoricalExchangeRatesPort;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getHistoricExchangeRate_returnListExchangeRate() {
        ExchangeRate c = new ExchangeRate().rate(BigDecimal.valueOf(0.82)).currency("CHF").date(LocalDate.parse("2020-10-01"));
        ExchangeRate c2 = new ExchangeRate().rate(BigDecimal.valueOf(0.78)).currency("CHF").date(LocalDate.parse("2020-10-03"));
        when(HistoricalExchangeRatesPort.findAllByIdentity_idCurrency(anyString(), any(Integer.class), any(Integer.class))).thenReturn(Arrays.asList(c, c2));

        List<ExchangeRate> l = HistoricalExchangeRatesService.getHistoricExchangeRate(null, null, "CHF", null);

        assertThat(l).hasSize(2);
        assertThat(l.get(0).getCurrency()).isEqualTo("CHF");
    }

    @Test
    public void getHistoricExchangeRate_returnOnlyOneExchangeRate() {
        ExchangeRate c = new ExchangeRate().rate(BigDecimal.valueOf(0.82)).currency("CHF").date(LocalDate.parse("2020-10-01"));
        when(HistoricalExchangeRatesPort.findAllByIdentity_idCurrency("CHF", 0, 1)).thenReturn(Collections.singletonList(c));

        List<ExchangeRate> l = HistoricalExchangeRatesService.getHistoricExchangeRate(0, 1, "CHF", null);

        assertThat(l).hasSize(1);
        assertThat(l.get(0).getCurrency()).isEqualTo("CHF");
        assertThat(l.get(0).getDate()).isEqualTo(LocalDate.parse("2020-10-01"));
    }

    @Test
    public void insertHistoricExchangeRate_returnExchangeRate() {
        ExchangeRate c = new ExchangeRate().rate(BigDecimal.valueOf(0.82)).currency("CHF").date(LocalDate.parse("2020-10-01"));
        when(HistoricalExchangeRatesPort.save(c)).thenReturn(c);

        ExchangeRate c1 = HistoricalExchangeRatesService.insertExchangeRate(c);

        assertThat(c1).isEqualTo(c);
    }

    @Test()
    public void insertHistoricExchangeRate_throwException() {
        ExchangeRate c = new ExchangeRate().rate(BigDecimal.valueOf(0.82)).currency("CHF").date(LocalDate.parse("2020-10-01"));
        when(HistoricalExchangeRatesPort.save(c)).thenThrow(new RuntimeException());

        assertThrows(ServiceUnavailableException.class, () -> HistoricalExchangeRatesService.insertExchangeRate(c));
    }

    @Test()
    public void deleteHistoricExchangeRate_throwEmptyResultDataAccessException() {
        doThrow(EmptyResultDataAccessException.class).when(HistoricalExchangeRatesPort).deleteHistExRate("CHF", LocalDate.parse("2020-01-01"));

        assertThrows(EmptyResultDataAccessException.class, () -> HistoricalExchangeRatesService.deleteExchangeRate("CHF", LocalDate.parse("2020-01-01")));
    }

    @Test()
    public void deleteHistoricExchangeRate_throwException() {
        doThrow(RuntimeException.class).when(HistoricalExchangeRatesPort).deleteHistExRate("CHF", LocalDate.parse("2020-01-01"));

        assertThrows(ServiceUnavailableException.class, () -> HistoricalExchangeRatesService.deleteExchangeRate("CHF", LocalDate.parse("2020-01-01")));
    }

    @Test()
    public void updateHistoricExchangeRate_returnUpdatedElement() {
        ExchangeRate c = new ExchangeRate().rate(BigDecimal.valueOf(0.82)).currency("CHF").date(LocalDate.parse("2020-10-01"));
        when(HistoricalExchangeRatesPort.updateHistExRate(c)).thenReturn(1);

        int i = HistoricalExchangeRatesService.updateExchangeRate(c);

        assertThat(i).isEqualTo(1);
    }

    @Test()
    public void updateHistoricExchangeRate_throwException() {
        ExchangeRate c = new ExchangeRate().rate(BigDecimal.valueOf(0.82)).currency("CHF").date(LocalDate.parse("2020-10-01"));
        when(HistoricalExchangeRatesPort.updateHistExRate(c)).thenThrow(new RuntimeException());

        assertThrows(ServiceUnavailableException.class, () -> HistoricalExchangeRatesService.updateExchangeRate(c));
    }

}
