package capitalone.interview.controller;

import capitalone.interview.client.ProjectedTransactionListClient;
import capitalone.interview.dto.SpendIncome;
import capitalone.interview.dto.TransactionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by minchanglong on 1/16/17.
 */
@RestController
@RequestMapping("/projectedTransactions")
public class PorjectedTransactionListController {

    @Autowired
    private ProjectedTransactionListClient projectedTransactionListClient;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<TransactionList> getProjectedTransactions(@RequestParam("year") long year, @RequestParam("month") long month) {
        projectedTransactionListClient.setYear(year);
        projectedTransactionListClient.setMonth(month);
        return projectedTransactionListClient.getResponseEntity();
    }

    @RequestMapping(value="/spendAndIncome", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity< Map<String, SpendIncome>> getProjectedSpendAndIncome(@RequestParam("year") long year, @RequestParam("month") long month) {
        TreeMap<String, SpendIncome> spendIncomeMap;
        projectedTransactionListClient.setYear(year);
        projectedTransactionListClient.setMonth(month);
        spendIncomeMap = (TreeMap<String, SpendIncome>) projectedTransactionListClient.getPredictSpendAndIncomeForCurrentMonth();
        return ResponseEntity.ok().body(spendIncomeMap);
    }
}
