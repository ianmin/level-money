package capitalone.interview.controller;

import capitalone.interview.dto.AccountList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by minchanglong on 1/15/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountListControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllAccountsTest() {
        ResponseEntity<AccountList> accountListResponseEntity =
                this.restTemplate.getForEntity("/accounts", AccountList.class);

        assertEquals(HttpStatus.OK, accountListResponseEntity.getStatusCode());
        assertEquals("no-error", accountListResponseEntity.getBody().getError());

        AccountList accountList = accountListResponseEntity.getBody();
        assertNotNull(accountList);
    }
}