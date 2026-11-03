package lab6.bai4;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ContactImpl extends UnicastRemoteObject implements ContactInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final HashMap<String, String> contactList = new HashMap<>();

	protected ContactImpl() throws RemoteException {
		super();
		contactList.putIfAbsent("An", "0347382716");
		contactList.putIfAbsent("Trung", "0894727163");
		contactList.putIfAbsent("Nghia", "0918263642");
	}

	@Override
	public void addContact(String name, String phone) throws RemoteException {
		contactList.putIfAbsent(name, phone);
	}

	@Override
	public String findContact(String name) throws RemoteException {
		return contactList.get(name);
	}

	@Override
	public boolean deleteContact(String name) throws RemoteException {
		return contactList.remove(name) != null;
	}
}
