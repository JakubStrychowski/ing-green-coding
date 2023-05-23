package pl.ing.green.transactions.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;
import java.math.BigDecimal;

/**
 * Transaction
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-05-06T15:43:02.480471400+02:00[Europe/Warsaw]")
public class Transaction {

  @JsonProperty("debitAccount")
  private String debitAccount;

  @JsonProperty("creditAccount")
  private String creditAccount;

  @JsonProperty("amount")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
  @JsonSerialize(using = BigDecimalSerializer.class)
  private BigDecimal amount;

    public Transaction() {
        
    }
  
    public Transaction(String debitAccount, String creditAccount, BigDecimal amount) {
        this.creditAccount = creditAccount;
        this.debitAccount = debitAccount;
        this.amount = amount;
    }

  public Transaction debitAccount(String debitAccount) {
    this.debitAccount = debitAccount;
    return this;
  }

  /**
   * Get debitAccount
   * @return debitAccount
  */
  @Size(min = 26, max = 26) 
  @Schema(name = "debitAccount", example = "3.2309111922661937E+25", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getDebitAccount() {
    return debitAccount;
  }

  public void setDebitAccount(String debitAccount) {
    this.debitAccount = debitAccount;
  }

  public Transaction creditAccount(String creditAccount) {
    this.creditAccount = creditAccount;
    return this;
  }

  /**
   * Get creditAccount
   * @return creditAccount
  */
  @Size(min = 26, max = 26) 
  @Schema(name = "creditAccount", example = "3.107431869813706E+25", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getCreditAccount() {
    return creditAccount;
  }

  public void setCreditAccount(String creditAccount) {
    this.creditAccount = creditAccount;
  }

  public Transaction amount(BigDecimal amount) {
      this.amount = amount;
      return this;
  }

  /**
   * Get amount
   * @return amount
  */
  
  @Schema(name = "amount", format = "0.00",  requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount.setScale(2);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction transaction = (Transaction) o;
    return Objects.equals(this.debitAccount, transaction.debitAccount) &&
        Objects.equals(this.creditAccount, transaction.creditAccount) &&
        Objects.equals(this.amount, transaction.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(debitAccount, creditAccount, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");
    sb.append("    debitAccount: ").append(toIndentedString(debitAccount)).append("\n");
    sb.append("    creditAccount: ").append(toIndentedString(creditAccount)).append("\n");
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

