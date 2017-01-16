package capitalone.interview.controller;

import capitalone.interview.dto.SpendIncome;
import capitalone.interview.dto.TransactionList;
import org.junit.Ignore;
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
import static org.junit.Assert.assertNotNull;

/**
 * Created by minchanglong on 1/15/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionListControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllTransactionsTest() {
        ResponseEntity<TransactionList> transactionListResponse =
                this.restTemplate.getForEntity("/transactions/all", TransactionList.class);

        assertEquals(HttpStatus.OK, transactionListResponse.getStatusCode());
        assertEquals("no-error", transactionListResponse.getBody().getError());

        TransactionList transactionList = transactionListResponse.getBody();
        assertNotNull(transactionList);
    }

    @Test
    public void getSpendAndIncomeMapWoPendingTransactions() {
        ResponseEntity<Map> spendIncomeMapResponse =
                this.restTemplate.getForEntity("/transactions/spendIncome",Map.class);

        assertEquals(HttpStatus.OK, spendIncomeMapResponse.getStatusCode());

        Map spendIncomeMap = spendIncomeMapResponse.getBody();
        assertNotNull(spendIncomeMap);
    }

    @Test
    public void getSpendAndIncomeMapWoDonutTransactions() {
        ResponseEntity<Map> spendIncomeMapResponse =
                this.restTemplate.getForEntity("/transactions/spendIncome/withoutDonut",Map.class);

        assertEquals(HttpStatus.OK, spendIncomeMapResponse.getStatusCode());

        Map spendIncomeMap = spendIncomeMapResponse.getBody();
        assertNotNull(spendIncomeMap);
    }

}