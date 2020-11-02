package it.ff.hexagonal.tdd.histexrate.codeimpl.adapters.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Data
@Builder
@Embeddable
public class HistExRateIdentity implements Serializable {
    private static final long serialVersionUID = -6871032108628387042L;

    @Column
    private String idCurrency;

    @Column
    private LocalDate idDate;

    public String getIdCurrency() {
        return idCurrency;
    }

    public LocalDate getIdDate() {
        return idDate;
    }

    public void setIdCurrency(String idCurrency) {
        this.idCurrency = idCurrency;
    }

    public void setIdDate(LocalDate idDate) {
        this.idDate = idDate;
    }

    public HistExRateIdentity(String idCurrency, LocalDate idDate) {
        this.idCurrency = idCurrency;
        this.idDate = idDate;
    }

    public HistExRateIdentity() {
    }
}