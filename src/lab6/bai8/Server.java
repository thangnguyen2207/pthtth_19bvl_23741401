package lab6.bai8;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            FlightServer flight = new FlightServer();

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("FlightService", flight);

            System.out.println("Flight Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
