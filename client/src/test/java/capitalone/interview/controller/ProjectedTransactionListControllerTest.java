package capitalone.interview.controller;

import capitalone.interview.dto.TransactionList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by minchanglong on 1/16/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectedTransactionListControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllProjectedTransactionsTest() {
        ResponseEntity<TransactionList> transactionListResponse =
                this.restTemplate.getForEntity("/projectedTransactions?year=2017&month=1", TransactionList.class);

        assertEquals(HttpStatus.OK, transactionListResponse.getStatusCode());
        assertEquals("no-error", transactionListResponse.getBody().getError());

        TransactionList transactionList = transactionListResponse.getBody();
        assertNotNull(transactionList);
        assertFalse(transactionList.getTransactionList().isEmpty());
    }

    @Test
    public void getPredictSpendAndIncomeForCurrentMonthTest() {
        ResponseEntity<Map> spendIncomeMapResponse =
                this.restTemplate.getForEntity("/projectedTransactions/spendAndIncome?year=2017&month=1", Map.class);

        assertEquals(HttpStatus.OK, spendIncomeMapResponse.getStatusCode());
        Map spendIncomeMap = spendIncomeMapResponse.getBody();
        assertNotNull(spendIncomeMap);
    }
}