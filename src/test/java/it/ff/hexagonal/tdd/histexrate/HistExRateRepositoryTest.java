package it.ff.hexagonal.tdd.histexrate;

import it.ff.hexagonal.tdd.histexrate.codeimpl.adapters.persistence.HistExRate;
import it.ff.hexagonal.tdd.histexrate.codeimpl.adapters.persistence.HistExRateIdentity;
import it.ff.hexagonal.tdd.histexrate.codeimpl.adapters.persistence.HistExRateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class HistExRateRepositoryTest {

    @Autowired
    private HistExRateRepository repository;

    @Test
    public void should_find_all_HistExRate() {
        Iterable<HistExRate> l = repository.findAll();
        assertThat(l).hasSize(4);
    }

    @Test
    public void should_find_all_currency_HistExRate() {
        Iterable<HistExRate> l = repository.findAllByIdentity_idCurrencyIgnoreCase("CHF", null);
        assertThat(l).hasSize(2);
        assertThat(l).allMatch(s -> s.getIdentity().getIdCurrency().equals("CHF"));
    }

    @Test
    public void should_find_none_currency_HistExRate() {
        Iterable<HistExRate> l = repository.findAllByIdentity_idCurrencyIgnoreCase("TRY", null);
        assertThat(l).hasSize(0);
    }

    @Test
    public void should_find_currency_and_date_HistExRate() {
        HistExRate l = repository.findAllByIdentity_idCurrencyAndIdentity_idDate("CHF", LocalDate.parse("2020-01-01"));
        assertThat(l).isNotNull();
        assertThat(l).matches(s -> s.getIdentity().getIdCurrency().equals("CHF") && s.getIdentity().getIdDate().equals(LocalDate.parse("2020-01-01")));
    }

    @Test
    public void should_throw_exception_on_delete_non_existent_HistExRate() {
        HistExRateIdentity s = new HistExRateIdentity("TRY", LocalDate.parse("2020-01-01"));
        assertThrows(EmptyResultDataAccessException.class, () -> repository.deleteById(s));
    }

    @Test
    public void should_return_empty_on_update_non_existent_HistExRate() {
        HistExRate s = new HistExRate(new HistExRateIdentity("TRY", LocalDate.parse("2020-01-01")), BigDecimal.valueOf(1.1));
        int i = repository.updateHistExRate(s);
        assertThat(i).isEqualTo(0);
    }

    @Test
    public void should_update_HistExRate() {
        HistExRate s = new HistExRate(new HistExRateIdentity("CHF", LocalDate.parse("2020-01-01")), BigDecimal.valueOf(0.56));
        int i = repository.updateHistExRate(s);
        assertThat(i).isEqualTo(1);
    }

    @Test
    public void should_insert_new_HistExRate() {
        HistExRate s = new HistExRate(new HistExRateIdentity("TRY", LocalDate.parse("2020-01-01")), BigDecimal.valueOf(0.56));
        HistExRate x = repository.save(s);
        assertThat(x).isEqualTo(s);
    }
}
