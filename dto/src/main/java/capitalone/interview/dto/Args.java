package capitalone.interview.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by minchanglong on 1/14/17.
 */
@Component
public class Args {

    @JsonProperty("api-token")
    private String apiToken;

    @JsonProperty("json-strict-mode")
    private boolean isJsonStrictMode;

    @JsonProperty("json-verbose-response")
    private boolean isJsonVerboseResponse;

    public Args(
            @Value("${login.api.token}") String apiToken,
            @Value("${login.json.strict.mode}") boolean isJsonStrictMode,
            @Value("${login.json.verbose.response}") boolean isJsonVerboseResponse) {
        this.apiToken = apiToken;
        this.isJsonStrictMode = isJsonStrictMode;
        this.isJsonVerboseResponse = isJsonVerboseResponse;
    }

    public String getApiToken() {
        return apiToken;
    }

    public boolean isJsonStrictMode() {
        return isJsonStrictMode;
    }

    public boolean isJsonVerboseResponse() {
        return isJsonVerboseResponse;
    }
}
