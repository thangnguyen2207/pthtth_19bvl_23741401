package lab6.bai8;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class FlightServer extends UnicastRemoteObject implements FlightInterface {

    private HashMap<String, Integer> flights = new HashMap<>();

    public FlightServer() throws RemoteException {
        super();

        flights.put("VN001", 100);
        flights.put("VN002", 80);
        flights.put("VN003", 120);
        flights.put("VN004", 50);
    }

    public synchronized boolean bookTicket(String flightNumber, int seats) throws RemoteException {
        if (seats > 0 && flights.containsKey(flightNumber)) {
            int available = flights.get(flightNumber);

            if (seats <= available) {
                flights.put(flightNumber, available - seats);
                return true;
            }
        }
        return false;
    }

    public synchronized int availableSeats(String flightNumber) throws RemoteException {
        if (flights.containsKey(flightNumber)) {
            return flights.get(flightNumber);
        }
        return -1;
    }

    public synchronized boolean cancelBooking(String flightNumber, int seats) throws RemoteException {
        if (seats > 0 && flights.containsKey(flightNumber)) {
            int available = flights.get(flightNumber);
            flights.put(flightNumber, available + seats);
            return true;
        }
        return false;
    }
}
