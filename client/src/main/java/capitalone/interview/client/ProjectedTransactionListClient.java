package capitalone.interview.client;

import capitalone.interview.dto.RequestBodyOfProjectedTransactionsForMonth;
import capitalone.interview.dto.SpendIncome;
import capitalone.interview.dto.Transaction;
import capitalone.interview.dto.TransactionList;
import capitalone.interview.logic.AccountsManager;
import capitalone.interview.logic.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

/**
 * Created by minchanglong on 1/16/17.
 */
@Component
public class ProjectedTransactionListClient extends Client {

    private final RestOperations restOperations;

    private final String url;

    private final TransactionListClient transactionListClient;

    private final TransactionManager transactionManager;

    private final AccountListClient accountListClient;

    private final AccountsManager accountsManager;

    private final RequestBodyOfProjectedTransactionsForMonth requestBodyOfProjectedTransactionsForMonth;

    private long year;

    private long month;

    public void setYear(long year) {
        this.year = year;
    }

    public void setMonth(long month) {
        this.month = month;
    }

    @Autowired
    public ProjectedTransactionListClient(
            @Value("${level.money.url.getProjectedTransactionsForMonth}") String url, RestOperations restOperations,
            TransactionListClient transactionListClient,  AccountsManager accountsManager,
            AccountListClient accountListClient, TransactionManager transactionManager,
            RequestBodyOfProjectedTransactionsForMonth requestBodyOfProjectedTransactionsForMonth) {
        this.restOperations = restOperations;
        this.url = url;
        this.transactionListClient = transactionListClient;
        this.transactionManager = transactionManager;
        this.accountListClient = accountListClient;
        this.accountsManager = accountsManager;
        this.requestBodyOfProjectedTransactionsForMonth = requestBodyOfProjectedTransactionsForMonth;
    }

    @Override
    public ResponseEntity<TransactionList> getResponseEntity() {
        configPostRequest();
        return restOperations.postForEntity(url, new HttpEntity<>(requestBodyOfProjectedTransactionsForMonth, getHeader()), TransactionList.class);
    }

    @Override
    public TransactionList getObject() {
        configPostRequest();
        return restOperations.postForObject(url, new HttpEntity<>(requestBodyOfProjectedTransactionsForMonth, getHeader()), TransactionList.class);
    }

    private void configPostRequest() {
        requestBodyOfProjectedTransactionsForMonth.setYear(year);
        requestBodyOfProjectedTransactionsForMonth.setMonth(month);
        requestBodyOfProjectedTransactionsForMonth.setArgs(super.getTokenArgs());
    }

    public Map<String, SpendIncome> getPredictSpendAndIncomeForCurrentMonth() {
        YearMonth yearMonth = YearMonth.now(ZoneId.from(ZoneOffset.UTC));

        String currentMonth = yearMonth.toString();
        Map<String, SpendIncome> spendIncomeMapFromAllTransactions = transactionListClient.getMonthlySpendAndIncome();
        Map<String, SpendIncome> spendIncomeMapFromProjectedTransactions = getProjectedSpendAndIncome();

        spendIncomeMapFromAllTransactions.put(currentMonth,
                transactionManager.combineSpendIncome(
                        spendIncomeMapFromAllTransactions.get(currentMonth),
                        spendIncomeMapFromProjectedTransactions.get(currentMonth)
                ));
        transactionManager.addMonthlyAverageSpendAndIncomeIntoMap(spendIncomeMapFromAllTransactions);
        return spendIncomeMapFromAllTransactions;
    }

    private Map<String, SpendIncome> getProjectedSpendAndIncome() {
        accountsManager.setAccountList(accountListClient.getObject());
        accountsManager.setAccountsMap();
        transactionManager.setAccountsManager(accountsManager);

        TransactionList transactionList = getObject();
        List<Transaction> transactions = transactionList.getTransactionList();
        List<Transaction> transactionsWithPending = transactionManager.filterNonPendingTransactions(transactions);

        return transactionManager.getSpendIncomeMapWoAverage(transactionsWithPending);
    }
}
