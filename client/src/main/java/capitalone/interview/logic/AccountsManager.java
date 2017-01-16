package capitalone.interview.logic;

import capitalone.interview.dto.Account;
import capitalone.interview.dto.AccountList;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by minchanglong on 1/15/17.
 */
@Component
public class AccountsManager {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AccountsManager.class);

    private AccountList accountList;

    private String accountIdToCheck;

    private Map<String, Account> accountsMap = Maps.newHashMap();

    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
    }

    public void setAccountIdToCheck(String accountIdToCheck) {
        this.accountIdToCheck = accountIdToCheck;
    }

    public boolean isAccountExist() {
        return accountsMap.containsKey(accountIdToCheck);
    }

    public boolean isAssetAccount() throws RuntimeException {

        if (!isAccountExist()) {
            throw new RuntimeException("Account Not Exists");
        }

        Account.AccountType accountType = accountsMap.get(accountIdToCheck).getAccountType();
        return accountType.equals(Account.AccountType.ASSET);
    }

    public Account.AssetAccountType getAssetAccountType() {
        if (!isAccountExist()) {
            throw new RuntimeException("Account Not Exists");
        }

       return accountsMap.get(accountIdToCheck).getAssetAccountType();
    }

    public void setAccountsMap() {
        List<Account> accounts = accountList.getAccounts();

        accountsMap = accounts.stream().collect(
                Collectors.toMap(Account::getAccountId, account -> account)
        );

        LOGGER.info("Accounts {} retrieve successfully", accountsMap);
    }
}
