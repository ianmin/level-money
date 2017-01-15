package capitalone.interview.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by minchanglong on 1/14/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Token extends Error{

    private Long uid;

    private String token;

    @JsonProperty("onboarding-stage")
    private String onboardingStage;

    @JsonProperty("agg-status")
    private String aggStatus;

    @JsonProperty("has-accounts-linked")
    private boolean hasAccountsLinked;

    public Token() {
        super();
    }

    public Token(String error, Long uid, String token, String onboardingStage, String aggStatus, boolean hasAccountsLinked) {
        super(error);
        this.uid = uid;
        this.token = token;
        this.onboardingStage = onboardingStage;
        this.aggStatus = aggStatus;
        this.hasAccountsLinked = hasAccountsLinked;
    }

    public Long getUid() {
        return uid;
    }

    public String getToken() {
        return token;
    }

    public String getOnboardingStage() {
        return onboardingStage;
    }

    public String getAggStatus() {
        return aggStatus;
    }

    public boolean isHasAccountsLinked() {
        return hasAccountsLinked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return hasAccountsLinked == token1.hasAccountsLinked &&
                Objects.equal(uid, token1.uid) &&
                Objects.equal(token, token1.token) &&
                Objects.equal(onboardingStage, token1.onboardingStage) &&
                Objects.equal(aggStatus, token1.aggStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uid, token, onboardingStage, aggStatus, hasAccountsLinked);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("error", getError())
                .add("uid", getUid())
                .add("token", getToken())
                .add("onboarding-stage", getOnboardingStage())
                .add("agg-status", getAggStatus())
                .add("has-accounts-linked", isHasAccountsLinked())
                .toString();
    }
}
