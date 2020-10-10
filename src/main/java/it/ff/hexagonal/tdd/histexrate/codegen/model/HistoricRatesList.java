package it.ff.hexagonal.tdd.histexrate.codegen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Objects;

/**
 * List of exchange rates
 */
@ApiModel(description = "List of exchange rates")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T22:05:16.885+02:00[Europe/Rome]")
public class HistoricRatesList implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("historic_rates")
  @Valid
  private java.util.List<ExchangeRate> historicRates = null;

  public HistoricRatesList historicRates(java.util.List<ExchangeRate> historicRates) {
    this.historicRates = historicRates;
    return this;
  }

  public HistoricRatesList addHistoricRatesItem(ExchangeRate historicRatesItem) {
    if (this.historicRates == null) {
      this.historicRates = new java.util.ArrayList<>();
    }
    this.historicRates.add(historicRatesItem);
    return this;
  }

  /**
   * Get historicRates
   *
   * @return historicRates
   */
  @ApiModelProperty(value = "")

  @Valid

  public java.util.List<ExchangeRate> getHistoricRates() {
    return historicRates;
  }

  public void setHistoricRates(java.util.List<ExchangeRate> historicRates) {
    this.historicRates = historicRates;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HistoricRatesList historicRatesList = (HistoricRatesList) o;
    return Objects.equals(this.historicRates, historicRatesList.historicRates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(historicRates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HistoricRatesList {\n");

    sb.append("    historicRates: ").append(toIndentedString(historicRates)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

