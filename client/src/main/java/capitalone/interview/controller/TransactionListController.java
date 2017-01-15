package capitalone.interview.controller;

import capitalone.interview.client.TransactionListClient;
import capitalone.interview.dto.TransactionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by minchanglong on 1/15/17.
 */
@RestController
public class TransactionListController {

    @Autowired
    TransactionListClient transactionListClient;

    @RequestMapping(value="/getAllTransactions", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<TransactionList> getAllTransactions() throws IOException {
        ResponseEntity<TransactionList> transactionList = transactionListClient.getResponseEntity();
        return transactionList;
    }

}
