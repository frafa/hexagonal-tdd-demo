package it.ff.hexagonal.tdd.histexrate.codeimpl.adapters.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name = "HistExRate")
public class HistExRate implements Serializable {
    private static final long serialVersionUID = -5089769005345422535L;

    @EmbeddedId
    private HistExRateIdentity identity;

    @Column
    private BigDecimal rate;

    public HistExRateIdentity getIdentity() {
        return identity;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setIdentity(HistExRateIdentity identity) {
        this.identity = identity;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public HistExRate(HistExRateIdentity identity, BigDecimal rate) {
        this.identity = identity;
        this.rate = rate;
    }

    public HistExRate() {
    }
}

