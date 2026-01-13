package Model;

//record individual banking operations such as deposits and withdrawals.
// sIt encapsulates transaction details like type, amount, timestamp, and account number.”

import java.time.LocalDateTime;

public class Transaction {

    private double amount;
    private String type;
    private LocalDateTime dateTime;
    private long accountNumber;

    public Transaction( long accountNumber, String type,double amount) {
        this.amount = amount;
        this.type = type;
        this.dateTime = LocalDateTime.now();
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public long getAccountNumber() {
        return accountNumber;
    }
}
