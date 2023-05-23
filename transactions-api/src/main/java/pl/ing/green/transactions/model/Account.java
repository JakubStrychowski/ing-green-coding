package pl.ing.green.transactions.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.*;
import jakarta.annotation.Generated;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Account
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-05-06T15:43:02.480471400+02:00[Europe/Warsaw]")
public class Account {

    public final static int ACCOUNT_NUMBER_LENGTH = 26;

    public static String randomAccountNumber() {
        Random random = new Random();
        char[] result = new char[ACCOUNT_NUMBER_LENGTH];
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            int digit = random.nextInt(0, 10);
            result[i] = (char) (digit + '0');
        }
        return new String(result);
    }

    @JsonProperty("account")
    private String account;

    @JsonProperty("debitCount")
    private int debitCount;

    @JsonProperty("creditCount")
    private int creditCount;

    @JsonProperty("balance")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal balance;

    
    public Account() {
        debitCount = 0;
        creditCount = 0;
        balance = new BigDecimal(0);
    }
    
    public Account(String accountNumber) {
        this.account = accountNumber;
        debitCount = 0;
        creditCount = 0;
        balance = new BigDecimal(0);
    }

    public void debit(BigDecimal amount) {
        debitCount++;
        balance = balance.subtract(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public void credit(BigDecimal amount) {
        creditCount++;
        balance = balance.add(amount).setScale(2, RoundingMode.HALF_UP);
    }

    public Account account(String account) {
        this.account = account;
        return this;
    }

    /**
     * Get account
     *
     * @return account
     */
    @Size(min = 26, max = 26)
    @Schema(name = "account", example = "3.2309111922661937E+25", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Account debitCount(int debitCount) {
        this.debitCount = debitCount;
        return this;
    }

    /**
     * Number of debit transactions
     *
     * @return debitCount
     */
    @Schema(name = "debitCount", example = "2", description = "Number of debit transactions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public int getDebitCount() {
        return debitCount;
    }

    public void setDebitCount(int debitCount) {
        this.debitCount = debitCount;
    }

    public Account creditCount(int creditCount) {
        this.creditCount = creditCount;
        return this;
    }

    /**
     * Number of credit transactions
     *
     * @return creditCount
     */
    @Schema(name = "creditCount", example = "2", description = "Number of credit transactions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public int getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(int creditCount) {
        this.creditCount = creditCount;
    }

    public Account balance(BigDecimal balance) {
        this.balance = balance.setScale(2, RoundingMode.HALF_UP);
        return this;
    }

    /**
     * Get balance
     *
     * @return balance
     */
    @Schema(name = "balance", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(this.account, account.account)
                && Objects.equals(this.debitCount, account.debitCount)
                && Objects.equals(this.creditCount, account.creditCount)
                && Objects.equals(this.balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, debitCount, creditCount, balance);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Account {\n");
        sb.append("    account: ").append(toIndentedString(account)).append("\n");
        sb.append("    debitCount: ").append(toIndentedString(debitCount)).append("\n");
        sb.append("    creditCount: ").append(toIndentedString(creditCount)).append("\n");
        sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
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
