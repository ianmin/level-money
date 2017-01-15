package capitalone.interview.client;

import capitalone.interview.dto.Credential;
import capitalone.interview.dto.Token;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TokenClient {
    @Autowired
    private RestOperations restOperations;

    private final String uri;

    @Autowired
    private Credential credential;

    @Autowired
    TokenClient(@Value("${level.money.url.login}") final String url) {
        this.uri = url;
    }

    public ResponseEntity<Token> getToken() {
        return restOperations.postForEntity(uri, getRequest(), Token.class);
    }

    private HttpEntity<Credential> getRequest() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> acceptList = new LinkedList<>();
        acceptList.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptList);

        return new HttpEntity<>(credential, headers);
    }
}
