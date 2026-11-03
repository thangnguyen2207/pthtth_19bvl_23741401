package lab6.bai3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrimeNumberInterface extends Remote {
	boolean isPrime(int n) throws RemoteException;
}
