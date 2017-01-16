package capitalone.interview.logic;

import capitalone.interview.client.DateConverter;
import capitalone.interview.dto.Account;
import capitalone.interview.dto.SpendIncome;
import capitalone.interview.dto.Transaction;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by minchanglong on 1/15/17.
 */
@Component
public class TransactionManager {

    private static final String DONUT_MERCHANT_ONE = "Krispy Kreme Donuts";
    private static final String DONUT_MERCHANT_TWO = "DUNKIN #336784";

    private final DateConverter dateConverter;

    private AccountsManager accountsManager;

    @Autowired
    public TransactionManager(DateConverter dateConverter) {
        this.dateConverter = dateConverter;
    }

    public void setAccountsManager(AccountsManager accountsManager) {
        this.accountsManager = accountsManager;
    }

    public List<Transaction> filterPendingTransactions(List<Transaction> transactions) {
        Predicate<Transaction> pendingTransactionsPredicate =
                transaction -> !transaction.isPending();

        return transactions.stream().parallel().filter(pendingTransactionsPredicate).collect(Collectors.toList());
    }

    public List<Transaction> filterDonutTransactions(List<Transaction> transactions) {
        Predicate<Transaction> donutTransactionsPredicate =
                transaction ->
                        !transaction.getMerchant().equalsIgnoreCase(DONUT_MERCHANT_ONE)
                                && !transaction.getMerchant().equalsIgnoreCase(DONUT_MERCHANT_TWO);

        return transactions.stream().parallel().filter(donutTransactionsPredicate).collect(Collectors.toList());
    }

    public Map<String, SpendIncome> getSpendIncomeMap(List<Transaction> transactionList) {
        Map<String, SpendIncome> spendIncomeMap = Maps.newTreeMap();
        transactionList.forEach(transaction -> {
            dateConverter.setDateTimeStr(transaction.getTransactionTime());
            String transactionYearMonth = dateConverter.getYearAndMonth();
            if (spendIncomeMap.containsKey(transactionYearMonth)) {
               SpendIncome combinedSpendIncome =
                       combineSpendIncome(spendIncomeMap.get(transactionYearMonth), transformTransaction(transaction));
               spendIncomeMap.put(transactionYearMonth, combinedSpendIncome);
            } else {
                spendIncomeMap.put(transactionYearMonth, transformTransaction(transaction));
            }
        });

        return spendIncomeMap;
    }

    private SpendIncome transformTransaction(Transaction transaction) {
        SpendIncome spendIncome = new SpendIncome();

        accountsManager.setAccountIdToCheck(transaction.getAccountId());
        if (accountsManager.isAssetAccount()
                && transaction.getAmount() >= 0) {
            spendIncome.setIncomeLong(Math.abs(transaction.getAmount()));
        } else if(accountsManager.isAssetAccount() && transaction.getAmount() < 0) {
            spendIncome.setSpendLong(Math.abs(transaction.getAmount()));
        } else if (accountsManager.isCreditAccount() && transaction.getAmount() <= 0) {
            spendIncome.setSpendLong(Math.abs(transaction.getAmount()));
        }

        return spendIncome;
    }

    private SpendIncome combineSpendIncome(SpendIncome spendIncomeLeft, SpendIncome spendIncomeRight) {
        SpendIncome spendIncomeCombined = new SpendIncome();
        spendIncomeCombined.setSpendLong(spendIncomeLeft.getSpendLong() + spendIncomeRight.getSpendLong());
        spendIncomeCombined.setIncomeLong(spendIncomeLeft.getIncomeLong() + spendIncomeRight.getIncomeLong());
        return spendIncomeCombined;
    }
}
