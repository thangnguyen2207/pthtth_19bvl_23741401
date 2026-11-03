package lab6.bai4;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ContactClient {

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", 6969);
			
			ContactInterface obj = (ContactInterface) registry.lookup("ContactService");
			
			obj.addContact("Phuc", "06488298372");
			
			String result = obj.findContact("Phuc");
			System.out.println("Ket qua tim kiem 'Phuc': " + result);
			
			boolean deleted = obj.deleteContact("Phuc");
			System.out.println(deleted ? "Da delete contact Phuc" : "Khong co thong tin contact hoac bi loi");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
