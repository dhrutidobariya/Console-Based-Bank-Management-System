package Model;

public class Account {
    private long accountNumber;
    private String accountHolderName;
    private String accountType;
    private double balance;



    public Account(long accountNumber, String accountHolderName, String accountType, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance =  balance<0?0:balance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }


    public boolean deposit(double amount)
    {
        if(amount>0)
        {
            balance+=amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount)
    {
        if(balance >= amount && amount>0)
        {
            balance-=amount;
            return true;

        }
        return false;
    }

}
