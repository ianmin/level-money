package capitalone.interview.client;

import capitalone.interview.dto.SpendIncome;
import capitalone.interview.dto.Transaction;
import capitalone.interview.dto.TransactionList;
import capitalone.interview.logic.AccountsManager;
import capitalone.interview.logic.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionListClient.class);

    private final RestOperations restOperations;

    private final String url;

    private final TransactionManager transactionManager;

    private final AccountListClient accountListClient;

    private final AccountsManager accountsManager;


    @Autowired
    TransactionListClient(@Value("${level.money.url.getAllTransactions}") final String url,
                          RestOperations restOperations, TransactionManager transactionManager,
                          AccountsManager accountsManager, AccountListClient accountListClient) {
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
        return getMonthlySpendAndIncome(false, false);
    }

    public Map<String, SpendIncome> getMonthlySpendAndIncomeWoDonut() {
        return getMonthlySpendAndIncome(true, false);
    }

    public Map<String, SpendIncome> getMonthlySpendAndIncomeWoCreditCardPayment() {
        return getMonthlySpendAndIncome(false, true);
    }

    private Map<String, SpendIncome> getMonthlySpendAndIncome(boolean filterDonut, boolean filterCreditCardPayment) {
        accountsManager.setAccountList(accountListClient.getObject());
        accountsManager.setAccountsMap();
        transactionManager.setAccountsManager(accountsManager);

        TransactionList transactionList = getObject();
        List<Transaction> transactions = transactionList.getTransactionList();
        List<Transaction> transactionsWoPending = transactionManager.filterPendingTransactions(transactions);

        LOGGER.info("Retrieved all transaction list size is {}", transactions.size());

        if (filterDonut) {
            List<Transaction> transactionsWoDonut = transactionManager.filterDonutTransactions(transactionsWoPending);
            return transactionManager.getSpendIncomeMap(transactionsWoDonut);
        }

        if (filterCreditCardPayment) {
            List<Transaction> transactionsWoCreditCardPayments = transactionManager.filterCreditCardPayments(transactionsWoPending);
            return transactionManager.getSpendIncomeMap(transactionsWoCreditCardPayments);
        }

        return transactionManager.getSpendIncomeMap(transactionsWoPending);
    }

    public List<Transaction> getCreditCardPayments() {
        accountsManager.setAccountList(accountListClient.getObject());
        accountsManager.setAccountsMap();
        transactionManager.setAccountsManager(accountsManager);

        TransactionList transactionList = getObject();
        List<Transaction> transactions = transactionList.getTransactionList();
        List<Transaction> transactionsWoPending = transactionManager.filterPendingTransactions(transactions);

        LOGGER.info("Retrieved all transaction list size is {}", transactions.size());

        transactionManager.filterCreditCardPayments(transactionsWoPending);
        return transactionManager.getCreditCardPaymentsTransactions();
    }




}
