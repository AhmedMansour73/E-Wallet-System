package service.impl;

import java.util.InputMismatchException;
import model.Account;
import service.AccountService;
import service.ApplicationService;
import service.ValidationService;

import java.util.Objects;
import java.util.Scanner;


public class ApplicationServiceImpl implements ApplicationService {

    private Scanner scanner = new Scanner(System.in);

    private AccountService accountService = new AccountServiceImpl();
    private ValidationService validationService = new ValidationServiceImpl();

    @Override
    public void startEWalletSystem() {
        System.out.println("Welcome sir :) ");
        int choose;
        int counter = 4;
        while (counter != 0) {
            try {
                System.out.println("Please choose number of operation.");
                System.out.println("1.SignUp \t 2.Login \n ");
                choose =scanner.nextInt();
                
                switch (choose) {
                case 1:
                    signUp();
                    break;
                case 2:
                    login();
                    break;
                default:
                    counter--;
                    System.out.println("Invalid choose. \t please try again.");
                    System.out.println("-----*********************************-----");
                }
                
                if( counter == 0)
                    System.out.println("You wasted all your attempts :( \n Try Later");  
                
            } catch (Exception e) {
                System.out.println(e+" \"expected number you entered string\"");
                scanner.next(); // Clear the invalid input from the buffer
                 counter--;
                 
            }
            
        }
    }

    /**
     * this function for login
     */
    private void login() {
        //  APPlY Validation for only username and password 
        Account account = validationService.validateLoginAccount();
        
        boolean isLogin = accountService.login(account);
        if (isLogin) {
            System.out.println("Login Success :)");
            loginFeatures(account);
        } else {
            System.out.println("invalid username or password :(");
        }
    }

    
    // Login  Feature (Operation on account)
    private void loginFeatures(Account account) {
        int choose;
        int counter = 4;
        // boolean isUserLogout = false;
        while (counter != 0) {
            try
            {
                System.out.println("please choose.");
                System.out.println("1.deposit   2.withdraw   3.transfer   4.show Account details  5.change password  6.logout");
                choose = scanner.nextInt();
                switch (choose) {
                    case 1:
                        deposit(account);
                        break;
                    case 2:
                        withdraw(account);
                        break;
                    case 3:
                        transfer(account);
                        break;
                    case 4:
                        showAccountDetails(account);
                        break;
                    case 5:
                        changePassword(account);
                        break;
                    case 6:
                        System.out.println("Have a nice Day :)");
//                        isUserLogout = true;
                        return ;
//                        break;
                        
                    default:
                        counter--;
                        System.out.println("Invalid choose");
                    }
                    if (counter == 0){
                        System.out.println("multi invalid choose please try later.");
                    }

//                    if (isUserLogout) {
//                        break;
//                    }
                
            }catch(Exception e)
            {
                System.out.println(e+" \"expected number you entered string\"");
                scanner.next(); // Clear the invalid input from the buffer
                 counter--;
                
            }
            
        }
    }

    
    private void withdraw(Account account) {
        try {
            System.out.println("Please enter the amount you want to withdraw:");
            double money = scanner.nextDouble();

            // Validate input
            if (money <= 0) {
                System.out.println("Withdrawal amount must be positive.");
                return;
            }

            int withdrawStatus = accountService.withDraw(account, money);

            switch (withdrawStatus) {
                case 1:
                    System.out.println("Account does not exist for withdrawal :(");
                    break;
                case 2:
                    System.out.println("Insufficient funds: The amount exceeds your account balance :(");
                    break;
                case 3:
                    System.out.println("Withdrawal successful :)");
                    break;
                default:
                    System.out.println("Unexpected error during withdrawal :(");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input: Please enter a valid number.");
            scanner.next(); // Clear the invalid input
        } catch (Exception e) {
            System.out.println("An error occurred during withdrawal: " + e.getMessage());
        }
    }

    private void showAccountDetails(Account account) {
        account = accountService.getAccount(account);
        if(Objects.isNull(account)){
            System.out.println("account not exist to show profile details");
            return;
        }

        System.out.println("-> userName:     " + account.getUserName());
        System.out.println("-> password:     " + account.getPassword());
        System.out.println("-> age:          " + account.getAge());
        System.out.println("-> phone number: " + account.getPhoneNumber());
        System.out.println("-> Balance:      " + account.getBalance());
    }

    private void deposit(Account account) {
        try {
            System.out.println("Please enter the amount you want to deposit:");
            double money = scanner.nextDouble();

            // Validate input
            if (money <= 0) {
                System.out.println("Deposit amount must be positive.");
                return;
            }

            boolean depositSuccess = accountService.deposit(account, money);

            if (depositSuccess) {
                System.out.println("Deposit successful :)");
                
            } else {
                System.out.println("Error: Account not found for deposit :(");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input: Please enter a valid number.");
            scanner.next(); // Clear the invalid input
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during deposit: " + e.getMessage());
            
        }
    }
    
    private void transfer(Account sourceAccount) {
        try {
            // Get recipient username
            System.out.println("Enter recipient's username:");
            String username = scanner.next();

            if (username.isEmpty()) {
                System.out.println("Error: Username cannot be empty");
                return;
            }

            // Prevent self-transfer
            if (username.equals(sourceAccount.getUserName())) {
                System.out.println("Error: Cannot transfer to yourself");
                return;
            }

            // Get transfer amount
            System.out.println("Enter amount to transfer:");
            double amount = scanner.nextDouble();

            // Validate amount
            if (amount <= 0) {
                System.out.println("Error: Amount must be positive");
                return;
            }

            // Execute transfer
            int transferStatus = accountService.transfer(sourceAccount, username, amount);

            // Handle transfer result
            switch (transferStatus) {
                case 4:
                    System.out.printf("Transfer of %.2f to %s successful%n", amount, username); 
                    break;
                case 3:
                    System.out.println("Error: Insufficient funds for transfer");
                    break;
                case 2:
                    System.out.println("Error: Recipient account not found");
                    break;
                case 1:
                    System.out.println("Error: Source account not found");
                    break;
                default:
                    System.out.println("Error: Unknown transfer error");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid amount entered");
            scanner.next();
        } catch (Exception e) {
            System.out.println("Error during transfer: " + e.getMessage());
            
        }
    }
    private void changePassword(Account account)
    {
        System.out.println("Enter old password");
        String oldPasswrd = scanner.next();
        System.out.println("Enter new password");
        String newPasswrd = scanner.next();
        
        if(validationService.isValidPassword(newPasswrd))
        {
            int changepassordSuccess = accountService.changePassword(account,oldPasswrd,newPasswrd);
            switch (changepassordSuccess) {
                case -1:
                    System.out.println("account not exist");
                    break;
                case 1:
                    System.out.println("change password success");
                    break;
                default:
                    System.out.println("your old password not coreect");
                    break;
            }
        }else
            System.out.println("Invalid new password\n password must have A or B ... and a or b ... and 1 or 2 ... and # or $ or @ or & n");
        
        
    }
    /**
     * this function for signUp
     */
    private void signUp() {
        Account account = validationService.validateCreateAccount();
        if(Objects.isNull(account)){
            return;
        }
        boolean isAccountCreated = accountService.createAccount(account);
        if (isAccountCreated) {
            System.out.println("Account Created Success :)");
        } else {
            System.out.println("there exist Account with same user name:(");
        }
    }

}
