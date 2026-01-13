import Model.Transaction;
import Service.BankService;

import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        BankService bank=new BankService();
        Scanner sc=new Scanner(System.in);
        boolean exit = false;
        while(!exit)
        {
            System.out.println("--------------------------------");
            System.out.println("1. Create a new Account");
            System.out.println("2. Log into exisiting  Account");
            System.out.println("3. Exit");

            System.out.println("--------------------------------");
            System.out.print("Enter your choice: ");
            int choice=sc.nextInt();
            switch (choice)
            {
                case 1:
                    System.out.print("Enter your Full name:");
                    sc.nextLine();
                    String name=sc.nextLine();

                    System.out.print("Choose Account Type(Saving/Current):");
                    String accountType=sc.next().toLowerCase();
                    System.out.print("Enter Initial Deposit:");
                    Double amount=sc.nextDouble();


                   long accNo= bank.createAccount(name,accountType,amount);
                    if(accNo==-1)
                    {
                        System.out.println("Account Not Created");

                    }
                    else
                    {
                        System.out.println("Account Created Successfully");
                        System.out.println("Your Account Number is: "+accNo);
                    }
                    break;

                case 2:
                    //login info
                    System.out.print("Enter account number: ");
                    long accountNumber = sc.nextLong();

                    if (!bank.validateAccount(accountNumber)) {
                        System.out.println("Invalid account number!");
                        break;
                    }

                    boolean logout = false;

                    while (!logout) {

                        System.out.println("\n--------- ACCOUNT MENU ---------");
                        System.out.println("1. Deposit");
                        System.out.println("2. Withdraw");
                        System.out.println("3. Check Balance");
                        System.out.println("4. Transaction History");
                        System.out.println("5. Logout");
                        System.out.print("Choose option: ");



                        int c = sc.nextInt();
                            switch (c) {
                                case 1:

                                    System.out.print("Enter a amount You Want to Deposit");
                                     amount=sc.nextDouble();
                                    if (bank.depositMoney(accountNumber,amount))
                                        System.out.println("Deposit Successfully");
                                    else
                                        System.out.println("Not Deposit");
                                    break;

                                case 2:

                                    System.out.print("Enter a amount You Want to Withdraw");
                                     amount=sc.nextDouble();
                                    if (bank.withdrawMoney(accountNumber,amount))
                                        System.out.println("Withdraw Successfully");
                                    else
                                        System.out.println("Not Withdraw");
                                    break;

                                case 3:
                                    System.out.println("Your Balance is: "+bank.getBalance(accountNumber));
                                    break;

                                case 4:
                                    List<Transaction> history= bank.transactionHistory(accountNumber);

                                    if (history.isEmpty())
                                        System.out.println("No History found");
                                    else
                                    {
                                        System.out.println("----------Transactions---------");
                                        for(Transaction t:history)
                                        {
                                            System.out.println(t.getType() +" | "+
                                                   t.getAmount() + " | "+
                                                    t.getDateTime());
                                        }
                                    }
                                    break;

                                case 5:
                                    logout = true;
                                    System.out.println("Logged out successfully.");
                                    break;

                                default:
                                    System.out.println("Invalid option!");
                            }
                        }
                    break;
                case 3:
                    exit=true;

                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        }
        sc.close();
        }
    }
