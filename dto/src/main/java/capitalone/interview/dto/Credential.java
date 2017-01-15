package capitalone.interview.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by minchanglong on 1/14/17.
 */
@Component
public class Credential {

    private final String email;

    private final String password;

    @Autowired
    private Args args;

    @Autowired
    public Credential(
            @Value("${login.email}") final String email,
            @Value("${login.password}") String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Args getArgs() {
        return args;
    }
}
