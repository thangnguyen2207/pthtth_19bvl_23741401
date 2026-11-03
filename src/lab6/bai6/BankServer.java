package lab6.bai6;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BankServer extends UnicastRemoteObject implements BankInterface {
    private double balance = 1000000;

    public BankServer() throws RemoteException {
        super();
    }

    public double getBalance() throws RemoteException {
        return balance;
    }

    public void deposit(double amount) throws RemoteException {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) throws RemoteException {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
