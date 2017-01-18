package capitalone.interview.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by minchanglong on 1/14/17.
 */
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Credential {

    private final String email;

    private final String password;

    @Autowired
    private Args args;

    public Credential() {
        this.email = null;
        this.password = null;
    }

    @Autowired
    public Credential(
            @Value("${login.email}") final String email,
            @Value("${login.password}") final String password) {
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

    public void setArgs(Args args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", args=" + args +
                '}';
    }
}
