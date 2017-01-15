package capitalone.interview.controller;

import capitalone.interview.dto.Token;
import capitalone.interview.client.TokenClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by minchanglong on 1/15/17.
 */
@RestController
public class TokenController {

    @Autowired
    private TokenClient tokenClient;

    @RequestMapping(value="/token", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Token> getToken() {
        ResponseEntity<Token> token = tokenClient.getResponseEntity();
        return token;
    }
}
