package lab6.bai2;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalNumServer {

	public static void main(String[] args) {
		try {
			CalNumImpl obj = new CalNumImpl();
			
			Registry registry = LocateRegistry.createRegistry(6969);
			
			registry.rebind("CalNumService", obj);
			
			System.out.println("Server dang chay");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
}
