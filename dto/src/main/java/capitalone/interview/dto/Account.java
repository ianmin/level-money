package capitalone.interview.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by minchanglong on 1/15/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    @JsonProperty("account-id")
    private String accountId;

    @JsonProperty("institution-id")
    private long institutionId;

    @JsonProperty("institution-name")
    private String institutionName;

    @JsonProperty("active")
    private boolean isActive;

    @JsonProperty("account-name")
    private String accountName;

    private long balance;

    @JsonProperty("account-type")
    private AccountType accountType;

    @JsonProperty("last-digits")
    private String lastDigits;

    @JsonProperty("institution-login-id")
    private String institutionLoginId;

    @JsonProperty("is-autosave-source")
    private boolean isAutoSaveSource;

    @JsonProperty("is-autosave-target")
    private boolean isAutoSaveTarget;

    @JsonProperty("can-be-autosave-funding-source")
    private boolean canBeAutoSaveFundingSource;

    @JsonProperty("can-be-autosave-target")
    private boolean canBeAutoSaveTarget;

    @JsonProperty("autosave-account-priority")
    private long autoSaveAccountPriority;

    @JsonProperty("asset-account-type")
    private AssetAccountType assetAccountType;

    @Autowired
    public Account() {

    }

    public Account(String accountId, long institutionId, String institutionName, boolean isActive, String accountName,
                   long balance, AccountType accountType, String lastDigits, String institutionLoginId,
                   boolean isAutoSaveSource, boolean isAutoSaveTarget, boolean canBeAutoSaveFundingSource,
                   boolean canBeAutoSaveTarget, long autoSaveAccountPriority, AssetAccountType assetAccountType) {
        this.accountId = accountId;
        this.institutionId = institutionId;
        this.institutionName = institutionName;
        this.isActive = isActive;
        this.accountName = accountName;
        this.balance = balance;
        this.accountType = accountType;
        this.lastDigits = lastDigits;
        this.institutionLoginId = institutionLoginId;
        this.isAutoSaveSource = isAutoSaveSource;
        this.isAutoSaveTarget = isAutoSaveTarget;
        this.canBeAutoSaveFundingSource = canBeAutoSaveFundingSource;
        this.canBeAutoSaveTarget = canBeAutoSaveTarget;
        this.autoSaveAccountPriority = autoSaveAccountPriority;
        this.assetAccountType = assetAccountType;
    }

    public String getAccountId() {
        return accountId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public AssetAccountType getAssetAccountType() {
        return assetAccountType;
    }

    public enum AccountType {
        UNRECOGNIZED,
        ASSET,
        CREDIT_CARD;

        private static Map<String, AccountType> namesMap = new HashMap<>(3);

        static {
            namesMap.put("unrecognized", UNRECOGNIZED);
            namesMap.put("asset", ASSET);
            namesMap.put("credit-card", CREDIT_CARD);
        }

        @JsonCreator
        public static AccountType forValue(String value) {
            return namesMap.get(value.toLowerCase());
        }

        @JsonValue
        public String toValue() {
            for (Map.Entry<String, AccountType> entry : namesMap.entrySet()) {
                if (entry.getValue() == this)
                    return entry.getKey();
            }

            return null;
        }
    }

    public enum AssetAccountType {
        UNKNOWN, SAVING, CHECKING, MONEY_MARKET,
        RECURRING_DEPOSIT, CD, CASH_MANAGEMENT,
        OVERDRAFT, PREPAID, NOT_AN_ASSET_ACCOUNT;

        private static Map<String, AssetAccountType> namesMap = new HashMap<>(10);

        static {
            namesMap.put("unknown", UNKNOWN);
            namesMap.put("savings", SAVING);
            namesMap.put("checking", CHECKING);
            namesMap.put("money-market", MONEY_MARKET);
            namesMap.put("recurring-deposit", RECURRING_DEPOSIT);
            namesMap.put("cd", CD);
            namesMap.put("cash-management", CASH_MANAGEMENT);
            namesMap.put("overdraft", OVERDRAFT);
            namesMap.put("prepaid", PREPAID);
            namesMap.put("not-an-asset-account", NOT_AN_ASSET_ACCOUNT);
        }

        @JsonCreator
        public static AssetAccountType forValue(String value) {
            return namesMap.get(value.toLowerCase());
        }

        @JsonValue
        public String toValue() {
            for (Map.Entry<String, AssetAccountType> entry : namesMap.entrySet()) {
                if (entry.getValue() == this)
                    return entry.getKey();
            }

            return null;
        }
    }
}



