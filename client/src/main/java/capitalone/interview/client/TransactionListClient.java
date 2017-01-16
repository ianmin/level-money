package capitalone.interview.client;

import capitalone.interview.dto.TransactionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

/**
 * Created by minchanglong on 1/14/17.
 */
@Component
public class TransactionListClient extends Client{

    private final RestOperations restOperations;

    private final String url;

    @Autowired
    TransactionListClient(@Value("${level.money.url.getAllTransactions}") final String url, RestOperations restOperations) {
        this.url = url;
        this.restOperations = restOperations;
    }

    public ResponseEntity<TransactionList> getResponseEntity() {
        return restOperations.postForEntity(url, getRequest(), TransactionList.class);
    }

    public TransactionList getObject() {
        return restOperations.postForObject(url, getRequest(), TransactionList.class);
    }

}
