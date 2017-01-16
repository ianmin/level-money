package capitalone.interview.client;

import capitalone.interview.dto.AccountList;
import capitalone.interview.dto.SpendIncome;
import capitalone.interview.dto.Transaction;
import capitalone.interview.dto.TransactionList;
import capitalone.interview.logic.AccountsManager;
import capitalone.interview.logic.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.Map;

/**
 * Created by minchanglong on 1/14/17.
 */
@Component
public class TransactionListClient extends Client{

    private final RestOperations restOperations;

    private final String url;

    private final TransactionManager transactionManager;

    private final AccountListClient accountListClient;

    private final AccountsManager accountsManager;

    @Autowired
    TransactionListClient(@Value("${level.money.url.getAllTransactions}") final String url,
                          RestOperations restOperations, TransactionManager transactionManager, AccountsManager accountsManager, AccountListClient accountListClient) {
        this.url = url;
        this.restOperations = restOperations;
        this.transactionManager = transactionManager;
        this.accountsManager = accountsManager;
        this.accountListClient = accountListClient;
    }

    public ResponseEntity<TransactionList> getResponseEntity() {
        return restOperations.postForEntity(url, getRequest(), TransactionList.class);
    }

    public TransactionList getObject() {
        return restOperations.postForObject(url, getRequest(), TransactionList.class);
    }

    public Map<String, SpendIncome> getMonthlySpendAndIncome() {
        return getMonthlySpendAndIncome(false);
    }

    public Map<String, SpendIncome> getMonthlySpendAndIncomeWoDonut() {
        return getMonthlySpendAndIncome(true);
    }

    private Map<String, SpendIncome> getMonthlySpendAndIncome(boolean filterDonut) {
        accountsManager.setAccountList(accountListClient.getObject());
        accountsManager.setAccountsMap();
        transactionManager.setAccountsManager(accountsManager);

        TransactionList transactionList = getObject();
        List<Transaction> transactions = transactionList.getTransactionList();
        List<Transaction> transactionsWoPending = transactionManager.filterPendingTransactions(transactions);

        if (filterDonut) {
            List<Transaction> transactionsWoDonut = transactionManager.filterDonutTransactions(transactionsWoPending);
            return transactionManager.getSpendIncomeMap(transactionsWoDonut);
        }

        return transactionManager.getSpendIncomeMap(transactionsWoPending);
    }

}
