package capitalone.interview.logic;

import capitalone.interview.client.DateConverter;
import capitalone.interview.dto.Account;
import capitalone.interview.dto.AccountList;
import capitalone.interview.dto.SpendIncome;
import capitalone.interview.dto.Transaction;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by minchanglong on 1/16/17.
 */
public class TransactionManagerTest {

    private TransactionManager transactionManager;

    private List<Transaction> transactions;

    private Transaction creditCardTransactionPending;
    private Transaction creditCardTransactionSpendingOne;
    private Transaction creditCardTransactionSpendingTwo;
    private Transaction creditCardTransactionSpendingThree;
    private Transaction creditCardTransactionSpendingFour;
    private Transaction creditCardDebitTransactionForPaymentsOne;
    private Transaction checkingTransactionForPaymentsOne;
    private Transaction creditCardDebitTransactionForPaymentsTwo;
    private Transaction checkingTransactionForPaymentsTwo;
    private Transaction checkingTransactionDepositOne;
    private Transaction checkingTransactionBillPayOne;
    private Transaction checkingTransactionDepositTwo;
    private Transaction checkingTransactionBillPayTwo;
    private Transaction checkingTransactionDepositThree;
    private Transaction checkingTransactionPending;
    private Transaction unKnownAccountTransaction;

    @Before
    public void setUp() throws Exception {
        Account assetAccount = new Account(
                "asset_account", 42069000, "NYBE (Not Your Bank Either)",
                true, "Checking Account", 59952400, Account.AccountType.ASSET,
                "1234", "nonce-institution-login:-535712", false,
                false, false, false, 0,
                Account.AssetAccountType.CHECKING
        );

        Account notAssetAccount = new Account(
                "credit_account", 42049001, "NYBE (Not Your Bank Either)",
                true, "Credit Card", 13023444, Account.AccountType.CREDIT_CARD,
                "1134", "nonce-institution-login:-535712", false,
                false, false, false, 0,
                Account.AssetAccountType.NOT_AN_ASSET_ACCOUNT
        );

        Account unRecognizedAccount = new Account(
                "unrecognized_account", 0, "NYBE (Not Your Bank Either)",
                true, "Unrecognized", 13023444, Account.AccountType.UNRECOGNIZED,
                "1134", "nonce-institution-login:-535712", false,
                false, false, false, 0,
                Account.AssetAccountType.UNKNOWN
        );

        List<Account> accounts = Lists.newArrayList();
        accounts.add(assetAccount);
        accounts.add(notAssetAccount);
        accounts.add(unRecognizedAccount);

        AccountList accountList = new AccountList();
        accountList.setError("no-error");
        accountList.setAccounts(accounts);

        AccountsManager accountsManager = new AccountsManager();
        accountsManager.setAccountList(accountList);
        accountsManager.setAccountsMap();

        DateConverter dateConverter = new DateConverter();

        transactionManager = new TransactionManager(dateConverter, accountsManager);

        creditCardTransactionPending = new Transaction(
                -200000, true, "credit_account", 1115535410000L, "1415535420001",
                "AMAZON","Unknown", "Amazon", "2017-01-06T07:17:00.000Z",
                "");

        //Credit Card Spending
        creditCardTransactionSpendingOne = new Transaction(
                -100000, false, "credit_account", 1115535420000L, "1415535420002",
                "AMAZON","Unknown", "Amazon", "2017-01-06T07:17:00.000Z",
                "");
        creditCardTransactionSpendingTwo = new Transaction(
                -260800, false, "credit_account", 1115535430000L, "1415535420003",
                "BEST BUY","Unknown", "Best Buy", "2016-12-07T07:17:00.000Z",
                "");

        //Credit Card Spending for Donuts
        creditCardTransactionSpendingThree = new Transaction(
                -111200, false, "credit_account", 1115535440000L, "1415535420004",
                "DUNKIN #336784","Unknown", "DUNKIN #336784", "2017-01-07T07:17:00.000Z",
                "");
        creditCardTransactionSpendingFour = new Transaction(
                -200000, false, "credit_account", 1115535410000L, "1415535420005",
                "Krispy Kreme Donuts","Unknown", "Krispy Kreme Donuts", "2016-12-06T07:17:00.000Z",
                "");

        //Payments deducted and posted in different date
        creditCardDebitTransactionForPaymentsOne = new Transaction(
                160000, false, "credit_account", 1115535450000L, "1415535420006",
                "Credit Card Payment","Unknown", "Credit Card Payment", "2017-01-01T06:17:00.000Z",
                "");
        checkingTransactionForPaymentsOne = new Transaction(
                -160000, false, "asset_account", 1115535460000L, "1415535420007",
                "CC Payment","CC Payment", "Credit Card Payment", "2016-12-31T07:17:00.000Z",
                "");

        //Payments deducted and posted in same date
        creditCardDebitTransactionForPaymentsTwo = new Transaction(
                60000, false, "credit_account", 1115535470000L, "1415535420008",
                "Credit Card Payment","Unknown", "Credit Card Payment", "2017-01-13T07:17:00.000Z",
                "");
        checkingTransactionForPaymentsTwo = new Transaction(
                -60000, false, "asset_account", 1115535480000L, "1415535420009",
                "CC Payment","CC Payment", "Credit Card Payment", "2017-01-13T06:17:00.000Z",
                "");

        //Checking Deposit and regular bill payment
        checkingTransactionDepositOne = new Transaction(
                30000000, false, "asset_account", 1115535490000L, "1415535420010",
                "ZENPAYROLL","Unknown", "Zenpayroll", "2017-01-10T07:17:00.000Z",
                "");
        checkingTransactionBillPayOne = new Transaction(
                -160800, false, "asset_account", 1115535510000L, "1415535420011",
                "AT&T BILL PAYMENT","Unknown", "At&T Bill Payment", "2017-01-11T07:17:00.000Z",
                "");
        checkingTransactionDepositTwo = new Transaction(
                30000000, false, "asset_account", 1115535520000L, "1415535420012",
                "ZENPAYROLL","Unknown", "Zenpayroll", "2016-12-10T07:17:00.000Z",
                "");
        checkingTransactionBillPayTwo = new Transaction(
                -160800, false, "asset_account", 1115535530000L, "1415535420013",
                "AT&T BILL PAYMENT","Unknown", "At&T Bill Payment", "2016-12-11T07:17:00.000Z",
                "");
        checkingTransactionDepositThree = new Transaction(
                220000, false, "asset_account", 1115535600000L, "1415535420014",
                "AT&T","Unknown", "AT&T", "2016-12-22T07:17:00.000Z",
                "");

        checkingTransactionPending = new Transaction(
                -300000, true, "asset_account", 1115535540000L, "1415535420015",
                "ZENPAYROLL","Unknown", "Zenpayroll", "2016-12-07T07:17:00.000Z",
                "");

        unKnownAccountTransaction = new Transaction(
                -100000, false, "unrecognized_account", 1115535550000L, "1515535420016",
                "BEST BUY","Unknown", "BEST BUY", "2016-12-07T07:17:00.000Z",
                "");

        transactions = Lists.newArrayList();
        transactions.add(creditCardTransactionSpendingOne);
        transactions.add(creditCardTransactionSpendingTwo);
        transactions.add(creditCardTransactionSpendingThree);
        transactions.add(creditCardTransactionSpendingFour);
        transactions.add(creditCardDebitTransactionForPaymentsOne);
        transactions.add(checkingTransactionForPaymentsOne);
        transactions.add(creditCardDebitTransactionForPaymentsTwo);
        transactions.add(checkingTransactionForPaymentsTwo);
        transactions.add(checkingTransactionDepositOne);
        transactions.add(checkingTransactionBillPayOne);
        transactions.add(checkingTransactionDepositTwo);
        transactions.add(checkingTransactionBillPayTwo);
        transactions.add(checkingTransactionDepositThree);
        transactions.add(unKnownAccountTransaction);
        transactions.add(creditCardTransactionPending);
        transactions.add(checkingTransactionPending);
    }

    @Test
    public void filterPendingTransactionsTest() {

        List<Transaction> expectedTransactionsForFilterPending = Lists.newArrayList();
        expectedTransactionsForFilterPending.add(creditCardTransactionSpendingOne);
        expectedTransactionsForFilterPending.add(creditCardTransactionSpendingTwo);
        expectedTransactionsForFilterPending.add(creditCardTransactionSpendingThree);
        expectedTransactionsForFilterPending.add(creditCardTransactionSpendingFour);
        expectedTransactionsForFilterPending.add(creditCardDebitTransactionForPaymentsOne);
        expectedTransactionsForFilterPending.add(checkingTransactionForPaymentsOne);
        expectedTransactionsForFilterPending.add(creditCardDebitTransactionForPaymentsTwo);
        expectedTransactionsForFilterPending.add(checkingTransactionForPaymentsTwo);
        expectedTransactionsForFilterPending.add(checkingTransactionDepositOne);
        expectedTransactionsForFilterPending.add(checkingTransactionBillPayOne);
        expectedTransactionsForFilterPending.add(checkingTransactionDepositTwo);
        expectedTransactionsForFilterPending.add(checkingTransactionBillPayTwo);
        expectedTransactionsForFilterPending.add(checkingTransactionDepositThree);
        expectedTransactionsForFilterPending.add(unKnownAccountTransaction);

        List<Transaction> actualFilterPendingTransactions = transactionManager.filterPendingTransactions(transactions);
        assertEquals(expectedTransactionsForFilterPending.size(), actualFilterPendingTransactions.size());
        assertEquals(expectedTransactionsForFilterPending, actualFilterPendingTransactions);
    }

    @Test
    public void filterDonutTransactionsTest() {
        List<Transaction> expectedTransactionsForFilterDonutMerchant = Lists.newArrayList();
        expectedTransactionsForFilterDonutMerchant.add(creditCardTransactionSpendingOne);
        expectedTransactionsForFilterDonutMerchant.add(creditCardTransactionSpendingTwo);
        expectedTransactionsForFilterDonutMerchant.add(creditCardDebitTransactionForPaymentsOne);
        expectedTransactionsForFilterDonutMerchant.add(checkingTransactionForPaymentsOne);
        expectedTransactionsForFilterDonutMerchant.add(creditCardDebitTransactionForPaymentsTwo);
        expectedTransactionsForFilterDonutMerchant.add(checkingTransactionForPaymentsTwo);
        expectedTransactionsForFilterDonutMerchant.add(checkingTransactionDepositOne);
        expectedTransactionsForFilterDonutMerchant.add(checkingTransactionBillPayOne);
        expectedTransactionsForFilterDonutMerchant.add(checkingTransactionDepositTwo);
        expectedTransactionsForFilterDonutMerchant.add(checkingTransactionBillPayTwo);
        expectedTransactionsForFilterDonutMerchant.add(checkingTransactionDepositThree);
        expectedTransactionsForFilterDonutMerchant.add(unKnownAccountTransaction);
        expectedTransactionsForFilterDonutMerchant.add(creditCardTransactionPending);
        expectedTransactionsForFilterDonutMerchant.add(checkingTransactionPending);

        List<Transaction> actualTransactionsForFilterDonutMerchant = transactionManager.filterDonutTransactions(transactions);
        assertEquals(expectedTransactionsForFilterDonutMerchant.size(), actualTransactionsForFilterDonutMerchant.size());
        assertEquals(expectedTransactionsForFilterDonutMerchant, actualTransactionsForFilterDonutMerchant);
    }

    @Test
    public void getSpendIncomeMapWithFilterPendingTransactionsTest() {
        Map<String, SpendIncome> expectedSpendIncomeMap = Maps.newHashMap();

        SpendIncome spendIncomeOne = new SpendIncome();
        spendIncomeOne.setSpendLong(432000L);
        spendIncomeOne.setIncomeLong(30000000L);
        expectedSpendIncomeMap.put("2017-01", spendIncomeOne);

        SpendIncome spendIncomeTwo = new SpendIncome();
        spendIncomeTwo.setSpendLong(781600L);
        spendIncomeTwo.setIncomeLong(30220000L);
        expectedSpendIncomeMap.put("2016-12", spendIncomeTwo);

        List<Transaction> transactionsWoPending = transactionManager.filterPendingTransactions(transactions);
        Map<String, SpendIncome> actualSpendIncomeMap = transactionManager.getSpendIncomeMap(transactionsWoPending);

        assertNotNull(actualSpendIncomeMap);
        assertEquals(expectedSpendIncomeMap, actualSpendIncomeMap);
        assertEquals(expectedSpendIncomeMap.size(), actualSpendIncomeMap.size());
    }

    @Test
    public void getSpendIncomeMapWithFilterDonutTransactionsTest() {
        Map<String, SpendIncome> expectedSpendIncomeMap = Maps.newHashMap();

        SpendIncome spendIncomeOne = new SpendIncome();
        spendIncomeOne.setSpendLong(320800L);
        spendIncomeOne.setIncomeLong(30000000L);
        expectedSpendIncomeMap.put("2017-01", spendIncomeOne);

        SpendIncome spendIncomeTwo = new SpendIncome();
        spendIncomeTwo.setSpendLong(581600L);
        spendIncomeTwo.setIncomeLong(30220000L);
        expectedSpendIncomeMap.put("2016-12", spendIncomeTwo);

        List<Transaction> transactionsWoPending = transactionManager.filterPendingTransactions(transactions);
        List<Transaction> transactionsWoDonut = transactionManager.filterDonutTransactions(transactionsWoPending);
        Map<String, SpendIncome> actualSpendIncomeMap = transactionManager.getSpendIncomeMap(transactionsWoDonut);

        assertNotNull(actualSpendIncomeMap);
        assertEquals(expectedSpendIncomeMap, actualSpendIncomeMap);
        assertEquals(expectedSpendIncomeMap.size(), actualSpendIncomeMap.size());
    }
}