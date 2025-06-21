package service;

import model.Account;

public interface AccountService {

    boolean createAccount(Account account);
    boolean login(Account account);

    boolean deposit(Account account, double money);
    int withDraw(Account account, double money);
    int transfer(Account account, String userNameAccountTransfer, double money);
    int changePassword(Account account , String oldpass , String newpass);
    Account getAccount(Account account);
    
}
