package it.ff.hexagonal.tdd.histexrate.codegen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Objects;

/**
 * Error response
 */
@ApiModel(description = "Error response")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T22:05:16.885+02:00[Europe/Rome]")
public class ResponseError implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("code")
  private String code;

  @JsonProperty("description")
  private String description;

  @JsonProperty("errorList")
  @Valid
  private java.util.List<ErrorDetail> errorList = null;

  public ResponseError code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   *
   * @return code
   */
  @ApiModelProperty(value = "")


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ResponseError description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   *
   * @return description
   */
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ResponseError errorList(java.util.List<ErrorDetail> errorList) {
    this.errorList = errorList;
    return this;
  }

  public ResponseError addErrorListItem(ErrorDetail errorListItem) {
    if (this.errorList == null) {
      this.errorList = new java.util.ArrayList<>();
    }
    this.errorList.add(errorListItem);
    return this;
  }

  /**
   * Get errorList
   *
   * @return errorList
   */
  @ApiModelProperty(value = "")

  @Valid

  public java.util.List<ErrorDetail> getErrorList() {
    return errorList;
  }

  public void setErrorList(java.util.List<ErrorDetail> errorList) {
    this.errorList = errorList;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResponseError responseError = (ResponseError) o;
    return Objects.equals(this.code, responseError.code) &&
            Objects.equals(this.description, responseError.description) &&
            Objects.equals(this.errorList, responseError.errorList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, description, errorList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResponseError {\n");

    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    errorList: ").append(toIndentedString(errorList)).append("\n");
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

