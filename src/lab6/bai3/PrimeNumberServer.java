package lab6.bai3;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PrimeNumberServer {

	public static void main(String[] args) {
		try {
			PrimeNumberImpl obj = new PrimeNumberImpl();
			
			Registry registry = LocateRegistry.createRegistry(6969);
			
			registry.rebind("PrimeNumberService", obj);
			
			System.out.println("Server dang chay");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
