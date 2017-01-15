package capitalone.interview.client;

import capitalone.interview.dto.Args;
import capitalone.interview.dto.Credential;
import capitalone.interview.dto.Token;
import capitalone.interview.dto.TransactionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by minchanglong on 1/14/17.
 */
@Component
public class TransactionListClient implements ClientInterface{

    @Autowired
    private RestOperations restOperations;

    @Autowired
    @Qualifier("requestApi")
    private Credential credential;

    @Autowired
    private Args args;

    @Autowired
    private TokenClient tokenClient;

    private final String url;

    @Autowired
    TransactionListClient(@Value("${level.money.url.getAllTransactions}") final String url) {
        this.url = url;
    }

    public ResponseEntity<TransactionList> getResponseEntity() {
        return restOperations.postForEntity(url, getRequest(), TransactionList.class);
    }

    public TransactionList getObject() {
        return restOperations.postForObject(url, getRequest(), TransactionList.class);
    }

    private HttpEntity<Credential> getRequest() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> acceptList = new LinkedList<>();
        acceptList.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptList);

        Token accessToken = tokenClient.getObject();
        args.setUid(accessToken.getUid());
        args.setToken(accessToken.getToken());
        credential.setArgs(args);

        return new HttpEntity<>(credential, headers);
    }

}
