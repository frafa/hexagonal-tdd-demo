package it.ff.hexagonal.tdd.histexrate.codegen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Object returned from convert_to operation
 */
@ApiModel(description = "Object returned from convert_to operation")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T22:05:16.885+02:00[Europe/Rome]")
public class ConvertedAmount implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("exchange_rates")
  @Valid
  private java.util.List<ExchangeRate> exchangeRates = new java.util.ArrayList<>();

  @JsonProperty("amount")
  private BigDecimal amount;

  public ConvertedAmount currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * Currency of converted amount
   *
   * @return currency
   */
  @ApiModelProperty(required = true, value = "Currency of converted amount ")
  @NotNull


  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public ConvertedAmount exchangeRates(java.util.List<ExchangeRate> exchangeRates) {
    this.exchangeRates = exchangeRates;
    return this;
  }

  public ConvertedAmount addExchangeRatesItem(ExchangeRate exchangeRatesItem) {
    this.exchangeRates.add(exchangeRatesItem);
    return this;
  }

  /**
   * List of exchange rates used to convert amount. The number of exchange rates is more than one if the base currency or the destination currency are not EUR.
   *
   * @return exchangeRates
   */
  @ApiModelProperty(required = true, value = "List of exchange rates used to convert amount. The number of exchange rates is more than one if the base currency or the destination currency are not EUR.")
  @NotNull

  @Valid

  public java.util.List<ExchangeRate> getExchangeRates() {
    return exchangeRates;
  }

  public void setExchangeRates(java.util.List<ExchangeRate> exchangeRates) {
    this.exchangeRates = exchangeRates;
  }

  public ConvertedAmount amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Converted amount
   *
   * @return amount
   */
  @ApiModelProperty(required = true, value = "Converted amount")
  @NotNull

  @Valid

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConvertedAmount convertedAmount = (ConvertedAmount) o;
    return Objects.equals(this.currency, convertedAmount.currency) &&
            Objects.equals(this.exchangeRates, convertedAmount.exchangeRates) &&
            Objects.equals(this.amount, convertedAmount.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currency, exchangeRates, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConvertedAmount {\n");

    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    exchangeRates: ").append(toIndentedString(exchangeRates)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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

