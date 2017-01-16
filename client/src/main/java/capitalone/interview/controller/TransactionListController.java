package capitalone.interview.controller;

import capitalone.interview.client.TransactionListClient;
import capitalone.interview.dto.SpendIncome;
import capitalone.interview.dto.TransactionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * Created by minchanglong on 1/15/17.
 */
@RestController
@RequestMapping("/transactions")
public class TransactionListController {

    @Autowired
    TransactionListClient transactionListClient;

    @RequestMapping(value="/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<TransactionList> getAllTransactions() throws IOException {
        return transactionListClient.getResponseEntity();
    }

    @RequestMapping(value="/spendIncome", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String, SpendIncome>> getMonthlySpendAndIncome() {
        Map<String, SpendIncome> spendIncomeMap = transactionListClient.getMonthlySpendAndIncome();
        return ResponseEntity.ok().body(spendIncomeMap);
    }

    @RequestMapping(value="/spendIncome/withoutDonut", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String, SpendIncome>> getMonthlySpendAndIncomeWoDonut() {
        Map<String, SpendIncome> spendIncomeMap = transactionListClient.getMonthlySpendAndIncomeWoDonut();
        return ResponseEntity.ok().body(spendIncomeMap);
    }

}
