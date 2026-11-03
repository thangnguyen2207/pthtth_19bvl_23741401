package lab6.bai3;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class PrimeNumberClient {

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", 6969);
			
			PrimeNumberInterface obj = (PrimeNumberInterface) registry.lookup("PrimeNumberService");
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Nhap n");
			int n = sc.nextInt();
			
			boolean result = obj.isPrime(n);
			
			System.out.println(n + " is" + (result? "" : " not") + " a prime number");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
