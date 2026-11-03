package lab6.bai9;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            AuctionServer auction = new AuctionServer();

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("AuctionService", auction);

            System.out.println("Auction Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}