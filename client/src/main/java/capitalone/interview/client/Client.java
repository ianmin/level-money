package capitalone.interview.client;

import capitalone.interview.dto.Args;
import capitalone.interview.dto.Credential;
import capitalone.interview.dto.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by minchanglong on 1/15/17.
 */
abstract class Client {

    private Credential credential;

    private Args args;

    private TokenClient tokenClient;

    public abstract ResponseEntity getResponseEntity();

    public abstract Object getObject();

    @Autowired
    @Qualifier("requestApi")
    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    @Autowired
    public void setArgs(Args args) {
        this.args = args;
    }

    @Autowired
    public void setTokenClient(TokenClient tokenClient) {
        this.tokenClient = tokenClient;
    }

    Args getTokenArgs() {
        Token accessToken = tokenClient.getObject();
        args.setUid(accessToken.getUid());
        args.setToken(accessToken.getToken());
        return args;
    }

    HttpEntity<Credential> getRequest() {
        HttpHeaders headers = getHeader();
        getTokenArgs();
        this.credential.setArgs(args);
        return new HttpEntity<>(credential, headers);
    }

    HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> acceptList = new LinkedList<>();
        acceptList.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptList);
        return headers;
    }
}
