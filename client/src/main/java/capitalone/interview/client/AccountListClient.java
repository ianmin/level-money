package capitalone.interview.client;

import capitalone.interview.dto.AccountList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

/**
 * Created by minchanglong on 1/15/17.
 */
@Component
public class AccountListClient extends Client{

    private final RestOperations restOperations;

    private final String url;

    @Autowired
    AccountListClient(@Value("${level.money.url.getAccounts}") final String url, RestOperations restOperations) {
        this.url = url;
        this.restOperations = restOperations;
    }

    public ResponseEntity<AccountList> getResponseEntity() {
        return restOperations.postForEntity(url, getRequest(), AccountList.class);
    }

    public AccountList getObject() {
        return restOperations.postForObject(url, getRequest(), AccountList.class);
    }
}
