package it.ff.hexagonal.tdd.histexrate;

import it.ff.hexagonal.tdd.histexrate.codegen.model.ExchangeRate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HistoricalExchangeRatesIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final BasicJsonTester json = new BasicJsonTester(getClass());

    @Test
    public void getHistoricalExchangeRates() {
        ResponseEntity<String> responseEntity = this.testRestTemplate.getForEntity("/historic-rate?currency={currency}", String.class, "CHF");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();

        JsonContent<?> jsonBody = json.from(responseEntity.getBody());
        assertThat(jsonBody).extractingJsonPathArrayValue("$.historic_rates[*].currency").containsOnly("CHF");

        responseEntity = this.testRestTemplate.getForEntity("/historic-rate?currency={currency}", String.class, "FAKE");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();

        jsonBody = json.from(responseEntity.getBody());
        assertThat(jsonBody).extractingJsonPathArrayValue("$.historic_rates").hasSize(0);

        responseEntity = this.testRestTemplate.getForEntity("/historic-rate?currency={currency}&date={date}", String.class, "CHF", "2020-01-01");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();

        jsonBody = json.from(responseEntity.getBody());
        assertThat(jsonBody).extractingJsonPathArrayValue("$.historic_rates").hasSize(1);

        responseEntity = this.testRestTemplate.getForEntity("/historic-rate?date={date}", String.class, "2020-01-01");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();

        jsonBody = json.from(responseEntity.getBody());
        assertThat(jsonBody).extractingJsonPathArrayValue("$.historic_rates").hasSize(2);

        responseEntity = this.testRestTemplate.getForEntity("/historic-rate", String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();

        jsonBody = json.from(responseEntity.getBody());
        assertThat(jsonBody).extractingJsonPathArrayValue("$.historic_rates").hasSize(4);
    }

    @Test
    public void insertExchangeRate() {
        ExchangeRate ExchangeRate = new ExchangeRate().currency("USD").rate(BigDecimal.valueOf(1.11)).date(LocalDate.parse("2020-03-01"));
        ResponseEntity<String> responseEntity = this.testRestTemplate.postForEntity("/historic-rate", ExchangeRate, String.class, (Object) null);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();

        JsonContent<?> jsonBody = json.from(responseEntity.getBody());
        assertThat(jsonBody).extractingJsonPathStringValue("$.currency").isEqualTo("USD");
        assertThat(jsonBody).extractingJsonPathNumberValue("$.rate").isEqualTo(1.11);
    }

    @Test
    public void updateExchangeRate() {
        ExchangeRate ExchangeRate = new ExchangeRate().currency("CHF").rate(BigDecimal.valueOf(1.11)).date(LocalDate.parse("2020-01-01"));
        HttpEntity<ExchangeRate> entity = new HttpEntity<>(ExchangeRate, null);
        ResponseEntity<String> responseEntity = this.testRestTemplate.exchange("/historic-rate", HttpMethod.PUT, entity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(responseEntity.getBody()).isNull();

        ExchangeRate = new ExchangeRate().currency("AUD").rate(BigDecimal.valueOf(1.11)).date(LocalDate.parse("2020-03-01"));
        entity = new HttpEntity<>(ExchangeRate, null);
        responseEntity = this.testRestTemplate.exchange("/historic-rate", HttpMethod.PUT, entity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isNotNull();
        JsonContent<?> jsonBody = json.from(responseEntity.getBody());
        assertThat(jsonBody).extractingJsonPathStringValue("$.code").isEqualTo("404");
    }

    @Test
    public void deleteExchangeRate() {
        ResponseEntity<String> responseEntity = this.testRestTemplate.exchange("/historic-rate?currency={currency}&date={date}", HttpMethod.DELETE, HttpEntity.EMPTY, String.class, "USD", "2020-01-01");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(responseEntity.getBody()).isNull();

        responseEntity = this.testRestTemplate.exchange("/historic-rate?currency={currency}&date={date}", HttpMethod.DELETE, HttpEntity.EMPTY, String.class, "USD", "2020-03-01");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isNotNull();
        JsonContent<?> jsonBody = json.from(responseEntity.getBody());
        assertThat(jsonBody).extractingJsonPathStringValue("$.code").isEqualTo("404");

        responseEntity = this.testRestTemplate.exchange("/historic-rate?currency={currency}", HttpMethod.DELETE, HttpEntity.EMPTY, String.class, "USD");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        jsonBody = json.from(responseEntity.getBody());
        assertThat(jsonBody).extractingJsonPathStringValue("$.code").isEqualTo("400");
    }

}
