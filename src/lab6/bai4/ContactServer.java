package lab6.bai4;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ContactServer {

	public static void main(String[] args) {
		try {
			ContactImpl obj = new ContactImpl();
			
			Registry registry = LocateRegistry.createRegistry(6969);
			
			registry.rebind("ContactService", obj);
			
			System.out.println("Server dang chay");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
