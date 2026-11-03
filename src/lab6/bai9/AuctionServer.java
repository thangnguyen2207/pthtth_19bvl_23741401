package lab6.bai9;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.Map;

public class AuctionServer extends UnicastRemoteObject implements AuctionInterface {

    private static class Product {
        int id;
        String name;
        double currentPrice;
        String highestBidder;

        Product(int id, String name, double startPrice) {
            this.id = id;
            this.name = name;
            this.currentPrice = startPrice;
            this.highestBidder = "Chưa có";
        }
    }

    private Map<Integer, Product> products = new LinkedHashMap<>();
    private int nextId = 1;

    public AuctionServer() throws RemoteException {
        super();
    }

    public synchronized boolean addProduct(String name, double startPrice) throws RemoteException {
        if (name == null || name.trim().isEmpty() || !Double.isFinite(startPrice) || startPrice <= 0) {
            return false;
        }

        Product product = new Product(nextId++, name, startPrice);
        products.put(product.id, product);

        System.out.println("New product added: " + name);
        return true;
    }

    public synchronized String getProducts() throws RemoteException {
        if (products.isEmpty()) {
            return "No products available!";
        }

        String result = "";

        for (Product p : products.values()) {
            result += "ID: " + p.id
                    + " | Name: " + p.name
                    + " | Current Price: " + p.currentPrice
                    + " | Highest Bidder: " + p.highestBidder
                    + "\n";
        }

        return result;
    }

    public synchronized boolean placeBid(int productId, String bidder, double bidPrice) throws RemoteException {
        Product product = products.get(productId);

        if (product == null || bidder == null || bidder.trim().isEmpty()
                || !Double.isFinite(bidPrice) || bidPrice <= product.currentPrice) {
            return false;
        }

        product.currentPrice = bidPrice;
        product.highestBidder = bidder;

        System.out.println("New highest bid!");
        System.out.println("Product: " + product.name);
        System.out.println("Bidder: " + bidder);
        System.out.println("New price: " + bidPrice);

        return true;
    }
}
