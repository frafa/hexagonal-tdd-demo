package it.ff.hexagonal.tdd.histexrate;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.ff.hexagonal.tdd.histexrate.codegen.api.HistoricRateApiDelegate;
import it.ff.hexagonal.tdd.histexrate.codegen.model.ExchangeRate;
import it.ff.hexagonal.tdd.histexrate.codeimpl.adapters.api.HistoricRateApiDelegateImpl;
import it.ff.hexagonal.tdd.histexrate.codeimpl.application.port.HistoricalExchangeRatesUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class HistoricalExchangeRatesWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HistoricalExchangeRatesUseCase HistoricalExchangeRatesUseCase;

    @TestConfiguration
    static class AdditionalConfiguration {
        @Bean
        public HistoricRateApiDelegate getHistoricalExchangeRatesApiDelegate() {
            return new HistoricRateApiDelegateImpl();
        }
    }

    @Test
    public void retrieve_should_get_cambio_storico_when_valid_request() throws Exception {
        List<ExchangeRate> l = new ArrayList<>();
        l.add(new ExchangeRate().rate(BigDecimal.valueOf(0.82)).currency("TRY").date(LocalDate.parse("2020-10-01")));
        when(HistoricalExchangeRatesUseCase.getHistoricExchangeRate(null, null, "TRY", null)).thenReturn(l);

        mockMvc.perform(get("/historic-rate?currency={currency}", "TRY")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.historic_rates[*].currency", everyItem(is("TRY"))));
    }

    @Test
    public void insert_should_insert_cambio_storico_when_valid_request() throws Exception {
        ExchangeRate c = new ExchangeRate().rate(BigDecimal.valueOf(0.77)).currency("USD").date(LocalDate.parse("2020-10-05"));
        when(HistoricalExchangeRatesUseCase.insertExchangeRate(any())).thenReturn(c);

        mockMvc.perform(post("/historic-rate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(c))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.currency", is("USD")))
                .andExpect(jsonPath("$.date", is("2020-10-05")))
                .andExpect(jsonPath("$.rate", is(0.77)));
    }

    @Test
    public void update_should_update_cambio_storico_when_valid_request() throws Exception {
        ExchangeRate c = new ExchangeRate().rate(BigDecimal.valueOf(0.77)).currency("USD").date(LocalDate.parse("2020-10-05"));
        when(HistoricalExchangeRatesUseCase.updateExchangeRate(any())).thenReturn(1);

        mockMvc.perform(put("/historic-rate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(c))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void update_should_return_404_when_not_valid_request() throws Exception {
        ExchangeRate c = new ExchangeRate().rate(BigDecimal.valueOf(0.77)).currency("USD").date(LocalDate.parse("2020-10-05"));
        when(HistoricalExchangeRatesUseCase.updateExchangeRate(any())).thenReturn(0);

        mockMvc.perform(put("/historic-rate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(c))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete_should_delete_cambio_storico_when_valid_request() throws Exception {
        doNothing().when(HistoricalExchangeRatesUseCase).deleteExchangeRate(anyString(), any(LocalDate.class));

        mockMvc.perform(delete("/historic-rate?currency={currency}&date={date}", "USD", "2020-01-01")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void delete_should_return_404_when_not_valid_request() throws Exception {
        doThrow(EmptyResultDataAccessException.class).when(HistoricalExchangeRatesUseCase).deleteExchangeRate(anyString(), any(LocalDate.class));

        mockMvc.perform(delete("/historic-rate?currency={currency}&date={date}", "USD", "2020-01-01")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
