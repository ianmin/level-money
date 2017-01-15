package capitalone.interview.client;

import org.springframework.http.ResponseEntity;

/**
 * Created by minchanglong on 1/15/17.
 */
public interface ClientInterface {
    ResponseEntity getResponseEntity();

    Object getObject();
}
