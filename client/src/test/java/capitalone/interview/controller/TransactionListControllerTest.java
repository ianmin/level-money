package capitalone.interview.controller;

import capitalone.interview.dto.TransactionList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.ws.Response;

import static org.junit.Assert.*;

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
                this.restTemplate.getForEntity("/getAllTransactions", TransactionList.class);

        assertEquals(HttpStatus.OK, transactionListResponse.getStatusCode());
        assertEquals("no-error", transactionListResponse.getBody().getError());

        TransactionList transactionList = transactionListResponse.getBody();
        assertNotNull(transactionList);
    }

}