package capitalone.interview.logic;

import capitalone.interview.client.DateConverter;
import capitalone.interview.dto.SpendIncome;
import capitalone.interview.dto.Transaction;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by minchanglong on 1/15/17.
 */
@Component
public class TransactionManager {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(TransactionManager.class);

    private static final String DONUT_MERCHANT_ONE = "Krispy Kreme Donuts";
    private static final String DONUT_MERCHANT_TWO = "DUNKIN #336784";
    private static final String CREDIT_CARD_PAYMENTS_MERCHANT_FOR_ASSET_ACCOUNT = "CC Payment";
    private static final String CREDIT_CARD_PAYMENTS_MERCHANT_FOR_NON_ASSET_ACCOUNT = "Credit Card Payment";

    private AccountsManager accountsManager;

    private List<Transaction> creditCardPaymentsTransactions = Lists.newLinkedList();

    @Autowired
    public TransactionManager() {}

    public void setAccountsManager(AccountsManager accountsManager) {
        this.accountsManager = accountsManager;
    }

    public List<Transaction> filterPendingTransactions(List<Transaction> transactions) {
        Predicate<Transaction> pendingTransactionsPredicate =
                transaction -> !transaction.isPending();

        return transactions.stream().parallel().filter(pendingTransactionsPredicate).collect(Collectors.toList());
    }

    public List<Transaction> filterNonPendingTransactions(List<Transaction> transactions) {
        Predicate<Transaction> nonPendingTransactionsPredicate = Transaction::isPending;

        return transactions.stream().parallel().filter(nonPendingTransactionsPredicate).collect(Collectors.toList());
    }

    public List<Transaction> filterDonutTransactions(List<Transaction> transactions) {
        Predicate<Transaction> donutTransactionsPredicate =
                transaction ->
                        !transaction.getMerchant().equalsIgnoreCase(DONUT_MERCHANT_ONE)
                                && !transaction.getMerchant().equalsIgnoreCase(DONUT_MERCHANT_TWO);

        return transactions.stream().parallel().filter(donutTransactionsPredicate).collect(Collectors.toList());
    }

    public List<Transaction> filterCreditCardPayments(List<Transaction> transactions) {
        List<String> creditCardPaymentTransactionIds = getTransactionIdsOfCreditCardPayments(transactions);

        List<Transaction> transactionsWithoutCreditCardPayments = Lists.newArrayList();

        transactions.forEach(transaction -> {
            if (creditCardPaymentTransactionIds.contains(transaction.getTransactionId())) {
                creditCardPaymentsTransactions.add(transaction);
            } else {
                transactionsWithoutCreditCardPayments.add(transaction);
            }
        });

        return transactionsWithoutCreditCardPayments;
    }

    public List<Transaction> getCreditCardPaymentsTransactions() {
        creditCardPaymentsTransactions.sort(Comparator.comparing(Transaction::getTransactionTime));
        return creditCardPaymentsTransactions;
    }

    public Map<String, SpendIncome> getSpendIncomeMap(List<Transaction> transactionList) {
        return getSpendIncomeMap(transactionList, true);
    }

    public Map<String, SpendIncome> getSpendIncomeMapWoAverage(List<Transaction> transactionList) {
        return getSpendIncomeMap(transactionList, false);
    }

    public Map<String, SpendIncome> getSpendIncomeMap(List<Transaction> transactionList, boolean isCaculateAverage) {
        Map<String, SpendIncome> spendIncomeMap = Maps.newTreeMap();
        transactionList.forEach(transaction -> {
            String transactionYearMonth = DateConverter.getYearAndMonth(transaction.getTransactionTime());
            if (spendIncomeMap.containsKey(transactionYearMonth)) {
               SpendIncome combinedSpendIncome =
                       combineSpendIncome(spendIncomeMap.get(transactionYearMonth), transformTransaction(transaction));
               spendIncomeMap.put(transactionYearMonth, combinedSpendIncome);
            } else {
                spendIncomeMap.put(transactionYearMonth, transformTransaction(transaction));
            }
        });

        if (isCaculateAverage) {
            addMonthlyAverageSpendAndIncomeIntoMap(spendIncomeMap);
        }

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

    public SpendIncome combineSpendIncome(SpendIncome spendIncomeLeft, SpendIncome spendIncomeRight) {

        if (spendIncomeLeft == null) {
            spendIncomeLeft = new SpendIncome();
        }

        if (spendIncomeRight == null) {
            spendIncomeRight = new SpendIncome();
        }

        SpendIncome spendIncomeCombined = new SpendIncome();
        spendIncomeCombined.setSpendLong(spendIncomeLeft.getSpendLong() + spendIncomeRight.getSpendLong());
        spendIncomeCombined.setIncomeLong(spendIncomeLeft.getIncomeLong() + spendIncomeRight.getIncomeLong());
        return spendIncomeCombined;
    }

    public void addMonthlyAverageSpendAndIncomeIntoMap(Map<String, SpendIncome> spendIncomeMap) {
        SpendIncome averageSpendIncome = new SpendIncome();

        long totalMonths = spendIncomeMap.size();
        long totalSpendLong = spendIncomeMap.values().stream().mapToLong(SpendIncome::getSpendLong).sum();
        long totalIncomeLong = spendIncomeMap.values().stream().mapToLong(SpendIncome::getIncomeLong).sum();

        averageSpendIncome.setSpendLong(totalSpendLong / totalMonths);
        averageSpendIncome.setIncomeLong(totalIncomeLong / totalMonths);

        spendIncomeMap.put("average", averageSpendIncome);
    }

    private List<String> getTransactionIdsOfCreditCardPayments(List<Transaction> transactions) {
        List<String> creditCardPaymentTransactionIds = Lists.newLinkedList();

        TreeMap<Long, Transaction> postedPaymentsOnChecking = Maps.newTreeMap();
        TreeMap<Long, Transaction> postedPaymentsOnCreditCard = Maps.newTreeMap();

        transactions.forEach(transaction -> {
            accountsManager.setAccountIdToCheck(transaction.getAccountId());
            if (accountsManager.isAssetAccount()
                    && transaction.getMerchant().equalsIgnoreCase(CREDIT_CARD_PAYMENTS_MERCHANT_FOR_ASSET_ACCOUNT)) {
                postedPaymentsOnChecking.put(DateConverter.getEpochSecond(transaction.getTransactionTime()), transaction);
            } else if (accountsManager.isCreditAccount()
                    && transaction.getMerchant().equalsIgnoreCase(CREDIT_CARD_PAYMENTS_MERCHANT_FOR_NON_ASSET_ACCOUNT)) {
                postedPaymentsOnCreditCard.put(DateConverter.getEpochSecond(transaction.getTransactionTime()), transaction);
            }
        });

        if (postedPaymentsOnChecking.size() != 0 && postedPaymentsOnCreditCard.size() != 0) {

            for (Map.Entry<Long, Transaction> paymentsOnCheckingEntry : postedPaymentsOnChecking.entrySet()) {
                Long transactionTimeChecking = paymentsOnCheckingEntry.getKey();
                Transaction transactionChecking = paymentsOnCheckingEntry.getValue();

                Iterator<Map.Entry<Long, Transaction>> iteratorOfPaymentOnCreditCard = postedPaymentsOnCreditCard.entrySet().iterator();
               while(iteratorOfPaymentOnCreditCard.hasNext()) {
                    Map.Entry<Long, Transaction> paymentsOnCreditCardEntry = iteratorOfPaymentOnCreditCard.next();
                    Long transactionTimeCreditCard = paymentsOnCreditCardEntry.getKey();
                    Transaction transactionCreditCard = paymentsOnCreditCardEntry.getValue();

                    if (transactionChecking.getAmount() + transactionCreditCard.getAmount() == 0
                            && DateConverter.isWithinTwentyFourHours(transactionTimeChecking, transactionTimeCreditCard)) {
                        creditCardPaymentTransactionIds.add(transactionChecking.getTransactionId());
                        creditCardPaymentTransactionIds.add(transactionCreditCard.getTransactionId());

                        iteratorOfPaymentOnCreditCard.remove();
                    }
                }
            }
        }

        return creditCardPaymentTransactionIds;
    }
}
