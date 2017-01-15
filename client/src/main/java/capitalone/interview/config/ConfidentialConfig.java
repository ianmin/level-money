package capitalone.interview.config;

import capitalone.interview.dto.Credential;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by minchanglong on 1/15/17.
 */
@Configuration
public class ConfidentialConfig {

    @Value("${login.email}")
    private String email;

    @Value("${login.password}")
    private String password;

    @Bean
    @Qualifier()
    public Credential createCredentialToGetAccessToken(){ return new Credential(email, password);}

    @Bean
    @Qualifier("requestApi")
    public Credential createCredentialForRequestApi(){ return new Credential();}

}
