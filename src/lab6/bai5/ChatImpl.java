package lab6.bai5;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatImpl extends UnicastRemoteObject implements ChatInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3918626649484434204L;

	protected ChatImpl() throws RemoteException {
		super();
	}

	@Override
	public void sendMessage(String message) {
		System.out.println("Client: " + message);
        System.out.println("Server: Da nhan tin nhan!");
	}

	
}
