package lab6.bai6;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            BankServer bank = new BankServer();

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("BankService", bank);

            System.out.println("Bank Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
