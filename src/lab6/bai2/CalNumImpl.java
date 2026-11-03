package lab6.bai2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalNumImpl extends UnicastRemoteObject implements CalNumInterface {

	protected CalNumImpl() throws RemoteException {
		super();
	}

	@Override
	public int add(int a, int b) throws RemoteException {
		return a + b;
	}

}
