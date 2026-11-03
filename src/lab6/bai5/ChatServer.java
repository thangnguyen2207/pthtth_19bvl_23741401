package lab6.bai5;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServer {

	public static void main(String[] args) {
		try {
			ChatImpl obj = new ChatImpl();
			
			Registry registry = LocateRegistry.createRegistry(6969);
			
			registry.rebind("ChatService", obj);
			
			System.out.println("Server dang chay");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
