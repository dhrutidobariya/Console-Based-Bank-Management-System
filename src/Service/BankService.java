package Service;
//Manages all banking operations
// Acts as a bridge between MainApp (UI) and model classes (Account, Transaction)

import Model.Account;
import Model.Transaction;


import javax.swing.event.ListDataEvent;
import java.util.*;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankService {

    Map<Long, Account> account = new HashMap<>();
    Map<Long, List<Transaction>> transaction = new HashMap<>();// one ccount can make multiple transaction so only put transaction object override previous transaction

    private static final Logger logger = Logger.getLogger(BankService.class.getName());

    long accountNumber=1000;
    long nextAccountNumber=accountNumber;


    public long createAccount(String accountHolderName,String type,double balance)
    {
         nextAccountNumber=nextAccountNumber+1;

         if((!accountHolderName.equals("")) && ((type.equals("saving")) || (type.equals("current"))) && balance>=0 )
        {
             Account acc=new Account(nextAccountNumber,accountHolderName,type,balance);
            account.put(nextAccountNumber,acc);

            logger.log(Level.INFO, "Account created successfully. Account Number: {0}",
                    nextAccountNumber);
                return nextAccountNumber;
        }
         return -1;
    }

    //To verify whether an account exists in the system and is eligible to be used.
    public boolean validateAccount(long accountNumber)
    {
        return account.containsKey(accountNumber);
    }

    //To deposit money into a valid account and record the transaction safely.
    public boolean depositMoney(long accountNumber,double amount)
    {
        Supplier<String> StrSupplier
                = () -> "Invalid deposit amount";
        // 1. Check if account exists
        if (!account.containsKey(accountNumber)) {
            logger.severe("Account not found.");
            return false;
        }
        // 2. Validate amount
        if (amount <= 0) {
           // logger.log(Level.SEVERE,new RuntimeException("Error"),StrSupplier);
            logger.warning("Invalid deposit amount.");
            return false;

        }

        // 3. Get existing account
        Account acc = account.get(accountNumber);

        // 4. Deposit money
        boolean success = acc.deposit(amount);

        if (success) {
            // 5. Create transaction
            Transaction trans = new Transaction(accountNumber, "DEPOSIT", amount);

            // 6. Store transaction (one account → many transactions)
            transaction.computeIfAbsent(accountNumber, k -> new ArrayList<>())
                    .add(trans);

            logger.log(Level.INFO,
                    "₹{0} deposited into Account {1}",
                    new Object[]{amount, accountNumber});
            /**
             * if (!transaction.containsKey(accountNumber)) {
             *     transaction.put(accountNumber, new ArrayList<>());
             * }
             *
             * transaction.get(accountNumber).add(trans);
             */

            return true;
        }
        return false;
    }

    //Withdraw money from a valid account, only if sufficient balance exists, and record the transaction.
    public boolean withdrawMoney(long accountNumber,double amount)
    {
        if(!account.containsKey(accountNumber)){
            logger.warning("Account not found.");
            return false;}
        if (amount <=0){
            logger.warning("Invalid withdrawal amount.");

            return false;
        }
        Account acc=account.get(accountNumber);

        if(acc.withdraw(amount))
        {
            Transaction trans=new Transaction(accountNumber, "WITHDRAW", amount);
            transaction.computeIfAbsent(accountNumber, k -> new ArrayList<>())
                    .add(trans);
            logger.log(Level.INFO,
                    "₹{0} withdrawn from Account {1}",
                    new Object[]{amount, accountNumber});
            return true;
        }

        return false;


    }

    public double getBalance(long accountNumber)
    {
        if (!account.containsKey(accountNumber))
        {   logger.warning("Account not found while checking balance.");
            return -1;
        }
        Account acc=account.get(accountNumber);
        return acc.getBalance();
    }

    public List<Transaction> transactionHistory(long accountNumber)
    {
        if (!account.containsKey(accountNumber))
        {
            logger.warning("Account not found while fetching transaction history.");
            return new ArrayList<>();

        }
         return transaction.getOrDefault(accountNumber, new ArrayList<>());
    }


}
