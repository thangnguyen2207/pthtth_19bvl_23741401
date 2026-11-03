package lab6.bai9;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuctionInterface extends Remote {
    boolean addProduct(String name, double startPrice) throws RemoteException;
    String getProducts() throws RemoteException;
    boolean placeBid(int productId, String bidder, double bidPrice) throws RemoteException;
}
