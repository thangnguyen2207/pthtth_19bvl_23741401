package lab6.bai1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloClient {
	
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", 6969);
			
			HelloInterface obj = (HelloInterface) registry.lookup("HelloService");
			
			String message = obj.sayHello();
			System.out.println("Server: " + message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
