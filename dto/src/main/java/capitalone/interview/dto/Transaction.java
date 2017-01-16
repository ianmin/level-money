package capitalone.interview.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * Created by minchanglong on 1/14/17.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {

    private long amount;

    @JsonProperty("is-pending")
    private boolean isPending;

    @JsonProperty("account-id")
    private String accountId;

    @JsonProperty("clear-date")
    private Long clearDate;

    @JsonProperty("transaction-id")
    private String transactionId;

    @JsonProperty("raw-merchant")
    private String rawMerchant;

    @JsonProperty("categorization")
    private String categorization;

    @JsonProperty("merchant")
    private String merchant;

    @JsonProperty("transaction-time")
    private String transactionTime;

    @JsonProperty("previous-transaction-id")
    private String previousTransactionId;

    public Transaction() {}

    public Transaction(int amount, boolean isPending, String accountId, Long clearDate, String transactionId,
                       String rawMerchant, String categorization, String merchant, String transaction_time,
                       String previous_transaction_id) {
        this.amount = amount;
        this.isPending = isPending;
        this.accountId = accountId;
        this.clearDate = clearDate;
        this.transactionId = transactionId;
        this.rawMerchant = rawMerchant;
        this.categorization = categorization;
        this.merchant = merchant;
        this.transactionTime = transaction_time;
        this.previousTransactionId = previous_transaction_id;
    }

    public long getAmount() {
        return amount;
    }

    public boolean isPending() {
        return isPending;
    }

    public String getAccountId() {
        return accountId;
    }

    public Long getClearDate() {
        return clearDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getRawMerchant() {
        return rawMerchant;
    }

    public String getCategorization() {
        return categorization;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public String getPreviousTransactionId() {
        return previousTransactionId;
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("amount", getAmount())
                .add("is-pending", isPending())
                .add("account-id", isPending())
                .add("clear-date", getClearDate())
                .add("transaction-id", getTransactionId())
                .add("raw-merchant", getRawMerchant())
                .add("categorization", getCategorization())
                .add("merchant", getMerchant())
                .add("transaction-time", getTransactionTime())
                .toString();
    }
}