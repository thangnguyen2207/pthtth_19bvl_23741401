package lab6.bai7;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            AreaServer area = new AreaServer();

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("AreaService", area);

            System.out.println("Area Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
