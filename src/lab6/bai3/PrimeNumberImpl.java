package lab6.bai3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrimeNumberImpl extends UnicastRemoteObject implements PrimeNumberInterface {

	protected PrimeNumberImpl() throws RemoteException {
		super();
	}

	@Override
	public boolean isPrime(int n) throws RemoteException {
		if (n < 2)
			return false;
		
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0)
				return false;
		}
		
		return true;
	}
}
