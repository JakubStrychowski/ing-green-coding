package pl.ing.green.atmservice.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * ATMs details
 */

@Schema(name = "ATM", description = "ATMs details")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-05-10T23:05:31.627387+02:00[Europe/Warsaw]")
public class ATM {

  @JsonProperty("region")
  private Integer region;

  @JsonProperty("atmId")
  private Integer atmId;

  public ATM region(Integer region) {
    this.region = region;
    return this;
  }

  public ATM() {
      
  }
  
  public ATM (int region, int atmId) {
    this.region = region;
    this.atmId = atmId;
  }
  
  /**
   * Get region
   * minimum: 1
   * maximum: 9999
   * @return region
  */
  @Min(1) @Max(9999) 
  @Schema(name = "region", example = "10", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Integer getRegion() {
    return region;
  }

  public void setRegion(Integer region) {
    this.region = region;
  }

  public ATM atmId(Integer atmId) {
    this.atmId = atmId;
    return this;
  }

  /**
   * Get atmId
   * minimum: 1
   * maximum: 9999
   * @return atmId
  */
  @Min(1) @Max(9999) 
  @Schema(name = "atmId", example = "500", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Integer getAtmId() {
    return atmId;
  }

  public void setAtmId(Integer atmId) {
    this.atmId = atmId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ATM ATM = (ATM) o;
    return Objects.equals(this.region, ATM.region) &&
        Objects.equals(this.atmId, ATM.atmId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(region, atmId);
  }

  @Override
  public String toString() {
      return region + "." +atmId;
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

