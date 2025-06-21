package service.impl;

import model.Account;
import service.ValidationService;

import java.util.Scanner;
import java.util.regex.Pattern;


public class ValidationServiceImpl implements ValidationService {


    private Scanner scanner = new Scanner(System.in);

    @Override
    public boolean isValidUserName(String userName) {
                String regEx="^[A-Z][a-zA-z]{2,}\\d*$"; 
                return Pattern.matches(regEx, userName);
    }

    @Override
    public boolean isValidPassword(String password) {
                String regEx="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@&])[A-Za-z\\d#$@&]{6,}$"; 
                return Pattern.matches(regEx, password);
    }

    @Override
    public boolean isValidAge(int age) {
                String ageAsString = Integer.toString(age);
                String regEx= "^1[89]|[2-7][0-9]|80$"; 
                return Pattern.matches(regEx , ageAsString);
    }

    @Override
    public boolean isValidPhoneNumber(String phoneNumber) {
                String regEx = "^201[0125]\\d{8}$";
                phoneNumber = "2"+(phoneNumber);
                return Pattern.matches(regEx , phoneNumber);
    }

    
    @Override
    public Account validateCreateAccount() {
        


        String userName = null;
        do {
                System.out.println("Please enter user name");
                userName = scanner.next();
                if (!isValidUserName(userName)) {
                        System.out.println("Note:\tInvalid username.");
                        System.out.println("username size must be greater than or equal 3 and start with upper case. \n");
                }
               
            }while (!isValidUserName(userName));

        
        String password = null;
        do {
                System.out.println("Please enter password");
                password = scanner.next();
                if (!isValidPassword(password)) {
                        System.out.println("invalid password.");
                        System.out.println("""
                               Password must be at least 6 characters long and include at least one uppercase letter,
                                one lowercase letter, one number, and one special character from the following: #, $, @, &.""");
                }
                
            }while (!isValidPassword(password));

        int age;
        do {
                System.out.println("Please enter age");
                age = scanner.nextInt();
                if (!isValidAge(age)) {
                        System.out.println("invalid age.");
                         System.out.println("age must be between [18, 80].");
                }
                
            }while (!isValidAge(age));
       

        String phoneNumber =null;
        do {
                System.out.println("Please enter phone number");
                phoneNumber = scanner.next();
                if (!isValidPhoneNumber(phoneNumber)) {
                        System.out.println("phone number.");
                        System.out.println("phone number must start with 2 and length must be 12.");
                }
                
            }while (!isValidPhoneNumber(phoneNumber));
        
        return new Account(userName, password, "2"+(phoneNumber), age);
    }

    @Override
    public Account validateLoginAccount() {
        // TODO Auto-generated method stub
        
        System.out.println("Please enter user name");
        String username = scanner.next();
        if(!isValidUserName(username)){
            System.out.println("Invalid username.\n username must be match pattern");
        }

        
        
        System.out.println("Please enter password");
        String password = scanner.next();
        if(!isValidPassword(password)){
            System.out.println("invalid password.\npassword not match pattern pf passwords");
        }
        
        return new Account(username, password);
    }
}
