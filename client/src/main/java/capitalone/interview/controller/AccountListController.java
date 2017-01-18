package capitalone.interview.controller;

import capitalone.interview.client.AccountListClient;
import capitalone.interview.dto.AccountList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by minchanglong on 1/15/17.
 */
@RestController
public class AccountListController {

    @Autowired
    AccountListClient accountListClient;

    @RequestMapping(value="/accounts", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<AccountList> getAllAccounts() {
        return accountListClient.getResponseEntity();
    }

}
