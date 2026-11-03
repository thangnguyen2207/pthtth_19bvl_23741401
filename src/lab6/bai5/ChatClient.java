package lab6.bai5;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatClient {

	public static void main(String[] args) {
		try {
			 Registry registry = LocateRegistry.getRegistry("localhost", 6969);

	            ChatInterface obj = (ChatInterface) registry.lookup("ChatService");

	            Scanner sc = new Scanner(System.in);

	            while (true) {
	                System.out.print("Client: ");
	                String message = sc.nextLine();

	                if (message.equalsIgnoreCase("exit")) {
	                    break;
	                }

	                obj.sendMessage(message);

	                System.out.println("Server: Da nhan tin nhan!");
	            }

	            sc.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
