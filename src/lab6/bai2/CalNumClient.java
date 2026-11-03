package lab6.bai2;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class CalNumClient {

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", 6969);
			
			CalNumInterface obj = (CalNumInterface) registry.lookup("CalNumService");
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Type a: ");
			int a = sc.nextInt();
			
			System.out.println("Type b:");
			int b = sc.nextInt();
			
			int result = obj.add(a, b);
			
			System.out.println("Server: " + a + " + " + b + " = " + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
