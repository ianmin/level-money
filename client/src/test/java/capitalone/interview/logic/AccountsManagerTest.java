package capitalone.interview.logic;

import capitalone.interview.dto.Account;
import capitalone.interview.dto.AccountList;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by minchanglong on 1/16/17.
 */
public class AccountsManagerTest {

    private AccountsManager accountsManager;

    @Before
    public void setUp() throws Exception {
        Account assetAccount = new Account(
                "asset_account", 42069000, "NYBE (Not Your Bank Either)",
                true, "Checking Account", 59952400, Account.AccountType.ASSET,
                "1234", "nonce-institution-login:-535712", false,
                false, false, false, 0,
                Account.AssetAccountType.CHECKING
        );

        Account notAssetAccount = new Account(
                "credit_account", 42049001, "NYBE (Not Your Bank Either)",
                true, "Credit Card", 13023444, Account.AccountType.CREDIT_CARD,
                "1134", "nonce-institution-login:-535712", false,
                false, false, false, 0,
                Account.AssetAccountType.NOT_AN_ASSET_ACCOUNT
        );

        Account unRecognizedAccount = new Account(
                "unrecognized_account", 0, "NYBE (Not Your Bank Either)",
                true, "Unrecognized", 13023444, Account.AccountType.UNRECOGNIZED,
                "1134", "nonce-institution-login:-535712", false,
                false, false, false, 0,
                Account.AssetAccountType.UNKNOWN
        );

        List<Account> accounts = Lists.newArrayList();
        accounts.add(assetAccount);
        accounts.add(notAssetAccount);
        accounts.add(unRecognizedAccount);

        AccountList accountList = new AccountList();
        accountList.setError("no-error");
        accountList.setAccounts(accounts);
        accountsManager = new AccountsManager();
        accountsManager.setAccountList(accountList);
        accountsManager.setAccountsMap();
    }

    @Test
    public void isAccountExistTest() throws Exception {
        accountsManager.setAccountIdToCheck("not exist account");
        assertFalse(accountsManager.isAccountExist());

        accountsManager.setAccountIdToCheck("asset_account");
        assertTrue(accountsManager.isAccountExist());

        accountsManager.setAccountIdToCheck("credit_account");
        assertTrue(accountsManager.isAccountExist());

        accountsManager.setAccountIdToCheck("unrecognized_account");
        assertTrue(accountsManager.isAccountExist());
    }

    @Test(expected=RuntimeException.class)
    public void getAccountTypeForNonExistAccountTest() throws Exception {
        accountsManager.setAccountIdToCheck("not exist account");
        accountsManager.isAssetAccount();
    }

    @Test
    public void getAccountTypeForExistAccountTest() {
        accountsManager.setAccountIdToCheck("asset_account");
        assertTrue(accountsManager.isAssetAccount());

        accountsManager.setAccountIdToCheck("credit_account");
        assertFalse(accountsManager.isAssetAccount());

        accountsManager.setAccountIdToCheck("unrecognized_account");
        assertFalse(accountsManager.isAssetAccount());
    }

    @Test(expected=RuntimeException.class)
    public void getAssetAccountTypeForNonExistAccountTest() throws Exception {
        accountsManager.setAccountIdToCheck("not exist account");
        accountsManager.getAssetAccountType();
    }

    @Test
    public void getAssetAccountTypeForExistAccountTest() throws Exception {
        accountsManager.setAccountIdToCheck("asset_account");
        assertEquals(Account.AssetAccountType.CHECKING, accountsManager.getAssetAccountType());

        accountsManager.setAccountIdToCheck("credit_account");
        assertEquals(Account.AssetAccountType.NOT_AN_ASSET_ACCOUNT, accountsManager.getAssetAccountType());

        accountsManager.setAccountIdToCheck("unrecognized_account");
        assertEquals(Account.AssetAccountType.UNKNOWN, accountsManager.getAssetAccountType());
    }
}