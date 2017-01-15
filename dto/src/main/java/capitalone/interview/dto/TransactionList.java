package capitalone.interview.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by minchanglong on 1/14/17.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionList extends Error{

    @JsonProperty("transactions")
    private List<Transaction> transactionList = Lists.newLinkedList();

    public TransactionList() {
        super();
    }

    public TransactionList(List<Transaction> transactionList) {
        super();
        this.transactionList = transactionList;
    }

    public TransactionList(String error, List<Transaction> transactionList) {
        super(error);
        this.transactionList = transactionList;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this)
                .add("error", getError())
                .add("transactions", getTransactionList()).toString();
    }
}
