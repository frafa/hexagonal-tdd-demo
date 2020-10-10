package it.ff.hexagonal.tdd.histexrate.codeimpl.adapters.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface HistExRateRepository extends PagingAndSortingRepository<HistExRate, HistExRateIdentity> {

//    @Query(value = "SELECT TOP 1 s.* "
//            + "FROM	HistExRate s "
//            + "WHERE "
//            + "currencyCode	= ?1 AND	dateRate <= ?2 "
//            + "ORDER BY	dateRate DESC", nativeQuery = true)
//    public HistExRate getCambioAtDate(String div, LocalDate date);

    List<HistExRate> findAllByIdentity_idCurrencyIgnoreCase(String currency, Pageable p);

    List<HistExRate> findAllByIdentity_idDate(LocalDate dateRate, Pageable p);

    List<HistExRate> findAllByIdentity_idCurrencyAndIdentity_idDate(String currency, LocalDate date, Pageable p);

    HistExRate findAllByIdentity_idCurrencyAndIdentity_idDate(String currency, LocalDate date);

    @Transactional
    @Modifying
    @Query(value = "UPDATE HistExRate SET rate=:#{#stc.rate} WHERE id_currency=:#{#stc.identity.idCurrency} AND id_date=:#{#stc.identity.idDate}")
    int updateHistExRate(@Param("stc") HistExRate HistoricalExchangeRateso);
}
