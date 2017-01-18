package capitalone.interview.controller;

import capitalone.interview.client.TransactionListClient;
import capitalone.interview.dto.SpendIncome;
import capitalone.interview.dto.Transaction;
import capitalone.interview.dto.TransactionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by minchanglong on 1/15/17.
 */
@RestController
@RequestMapping("/transactions")
public class TransactionListController {

    @Autowired
    private TransactionListClient transactionListClient;

    @RequestMapping(value="/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<TransactionList> getAllTransactions() {
        return transactionListClient.getResponseEntity();
    }

    @RequestMapping(value="/spendIncome", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String, SpendIncome>> getMonthlySpendAndIncome() {
        TreeMap<String, SpendIncome> spendIncomeMap;
        spendIncomeMap = (TreeMap<String, SpendIncome>) transactionListClient.getMonthlySpendAndIncome();
        return ResponseEntity.ok().body(spendIncomeMap);
    }

    @RequestMapping(value="/spendIncome/withoutDonut", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String, SpendIncome>> getMonthlySpendAndIncomeWoDonut() {
        TreeMap<String, SpendIncome> spendIncomeMap;
        spendIncomeMap = (TreeMap<String, SpendIncome>) transactionListClient.getMonthlySpendAndIncomeWoDonut();
        return ResponseEntity.ok().body(spendIncomeMap);
    }

    @RequestMapping(value="/spendIncome/withoutCreditCardPayment", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String, SpendIncome>> getMonthlySpendAndIncomeWoCreditCardPayment() {
        TreeMap<String, SpendIncome> spendIncomeMap;
        spendIncomeMap = (TreeMap<String, SpendIncome>) transactionListClient.getMonthlySpendAndIncomeWoCreditCardPayment();
        return ResponseEntity.ok().body(spendIncomeMap);
    }

    @RequestMapping(value="/getCreditCardPayments", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity< List<Transaction>> getCreditCardPayments() {
        List<Transaction> creditCardPayments;
        creditCardPayments = transactionListClient.getCreditCardPayments();
        return ResponseEntity.ok().body(creditCardPayments);
    }
}
