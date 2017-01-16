package capitalone.interview.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by minchanglong on 1/15/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountList extends Error{

    private List<Account> accounts;

    public AccountList() {
        super();
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}