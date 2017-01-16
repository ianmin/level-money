package capitalone.interview.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * Created by minchanglong on 1/14/17.
 */
@Configuration
public class RestConfig {

    @Bean
    public RestOperations createRestTemplate(final ClientHttpRequestFactory ClientHttpRequestFactory) {
        return new RestTemplate(ClientHttpRequestFactory);
    }

    @Bean
    public ClientHttpRequestFactory createClientHttpRequestFactory(
            @Value("${connect.time.out}") final int connectTimeOut,
            @Value("${read.time.out}") final int readTimeOut) {

        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory();
        httpComponentsClientHttpRequestFactory.setReadTimeout(readTimeOut);
        httpComponentsClientHttpRequestFactory.setConnectTimeout(connectTimeOut);
        return httpComponentsClientHttpRequestFactory;
    }
}
