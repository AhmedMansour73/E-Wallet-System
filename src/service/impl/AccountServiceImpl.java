package service.impl;

import model.Account;
import model.EWallet;
import service.AccountService;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private EWallet eWallet = new EWallet();

    @Override
    public boolean createAccount(Account account) {
        
        if (checkIfAccountExist(account) != -1) {
            return false;
        }
        eWallet.getAccounts().add(account);
        return true;
    }


    @Override
    public boolean login(Account account) {
        List<Account> accounts = eWallet.getAccounts();
        for (Account acc: accounts){
            if (acc.getUserName().equals(account.getUserName()) &&
                    acc.getPassword().equals(account.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deposit(Account account, double money) {
        int accountIndex = checkIfAccountExist(account);
        if (accountIndex == -1) {
            return false;
        }

        eWallet.getAccounts().get(accountIndex).setBalance(
                eWallet.getAccounts().get(accountIndex).getBalance() + money);

        return true;
    }

    @Override
    public int withDraw(Account account, double money) { // 3 result  true  false  false
        int accountIndex = checkIfAccountExist(account);
        if (accountIndex == -1) {
            return 1;
        }

        if (!(eWallet.getAccounts().get(accountIndex).getBalance() >= money)){
            return 2;
        }

        eWallet.getAccounts().get(accountIndex).setBalance(
                eWallet.getAccounts().get(accountIndex).getBalance() - money );

        return 3;
    }

    
    @Override
    public int transfer(Account account, String userNameAccountTransfer, double money) {
        // TODO if account not exist return 1
        int accountIndex = checkIfAccountExist(account);
        if (accountIndex == -1) {
            return 1;
        }
        // TODO if userNameAccountTransfer not exist return 2
        int usernameIndex = checkIfUsernameIsExit(userNameAccountTransfer);
        if( usernameIndex == -1)
            return 2;
        // TODO if money that will transfer is greater than account balance return 3
        if(money > eWallet.getAccounts().get(accountIndex).getBalance())
            return 3;
        
        // TODO If all above is true now ready to transfer
        // TODO transfer process cut money from account and add to userNameAccountTransfer then return 4
        try
        {
            eWallet.getAccounts().get(accountIndex).setBalance(
                eWallet.getAccounts().get(accountIndex).getBalance() - money );
            eWallet.getAccounts().get(usernameIndex).setBalance(eWallet.getAccounts().get(usernameIndex).getBalance() + money);
            
        }catch(Exception e)
        {
            System.out.println("transfer opartion not completed");
        }
        return 4;
    }

    @Override
    public int changePassword(Account account, String oldpass , String newpass) {
        int accountIndex = checkIfAccountExist(account);
        if (accountIndex == -1) {
            return -1;
        }
        
        if( eWallet.getAccounts().get(accountIndex).getPassword().equals(oldpass) )
        {
            eWallet.getAccounts().get(accountIndex).setPassword(newpass);
            return 1;
        }
        return 2;
    }
    
    
    @Override
    public Account getAccount(Account account) {
        int accountIndex = checkIfAccountExist(account);
        if (accountIndex == -1) {
            return null;
        }
        return eWallet.getAccounts().get(accountIndex);
    }


    private int checkIfAccountExist(Account account) {
        List<Account> accounts = eWallet.getAccounts();
        for (int i=0;i<accounts.size();i++){
            if (accounts.get(i).getUserName().equals(account.getUserName())) {
                return i;
            }
        }
        return -1;
    }
    
    private int checkIfUsernameIsExit(String username) {

       
        for (int i=0; i < eWallet.getAccounts().size() ; i++ ){
            if (eWallet.getAccounts().get(i).getUserName().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    
    
}
