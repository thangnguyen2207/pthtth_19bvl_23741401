package lab6.bai1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloServer {

	public static void main(String[] args) {
		try {
			HelloImpl obj = new HelloImpl();
			
			Registry registry = LocateRegistry.createRegistry(6969);
			
			registry.rebind("HelloService", obj);
			
			System.out.println("Server is ready");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
}
