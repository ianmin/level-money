package capitalone.interview.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by minchanglong on 1/14/17.
 */
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Args {

    private Long uid;

    private String token;

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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uid", getUid())
                .add("token", getToken())
                .add("api-token", getApiToken())
                .add("json-strict-mode", isJsonStrictMode())
                .add("json-verbose-response", isJsonVerboseResponse()).toString();
    }
}
