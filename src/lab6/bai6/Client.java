package lab6.bai6;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            BankInterface bank = (BankInterface) registry.lookup("BankService");

            Scanner sc = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\n===== BANKING SYSTEM =====");
                System.out.println("1. Check Balance");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");

                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Balance: " + bank.getBalance());
                        break;

                    case 2:
                        System.out.print("Enter amount to deposit: ");
                        double deposit = sc.nextDouble();

                        if (deposit > 0) {
                            bank.deposit(deposit);
                            System.out.println("Deposit successful!");
                        } else {
                            System.out.println("Invalid amount!");
                        }
                        break;

                    case 3:
                        System.out.print("Enter amount to withdraw: ");
                        double withdraw = sc.nextDouble();

                        if (bank.withdraw(withdraw)) {
                            System.out.println("Withdraw successful!");
                        } else {
                            System.out.println("Insufficient balance or invalid amount!");
                        }
                        break;

                    case 0:
                        System.out.println("Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } while (choice != 0);

            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
