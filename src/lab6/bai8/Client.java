package lab6.bai8;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            FlightInterface flight = (FlightInterface) registry.lookup("FlightService");

            Scanner sc = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\n===== FLIGHT BOOKING SYSTEM =====");
                System.out.println("1. Book Ticket");
                System.out.println("2. Check Available Seats");
                System.out.println("3. Cancel Booking");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");

                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter flight number: ");
                        String flightNumber = sc.next();

                        System.out.print("Enter number of seats: ");
                        int seats = sc.nextInt();

                        if (flight.bookTicket(flightNumber, seats)) {
                            System.out.println("Booking successful!");
                        } else {
                            System.out.println("Booking failed!");
                        }
                        break;

                    case 2:
                        System.out.print("Enter flight number: ");
                        String flightCheck = sc.next();

                        int available = flight.availableSeats(flightCheck);

                        if (available >= 0) {
                            System.out.println("Available seats: " + available);
                        } else {
                            System.out.println("Flight not found!");
                        }
                        break;

                    case 3:
                        System.out.print("Enter flight number: ");
                        String flightCancel = sc.next();

                        System.out.print("Enter number of seats to cancel: ");
                        int cancelSeats = sc.nextInt();

                        if (flight.cancelBooking(flightCancel, cancelSeats)) {
                            System.out.println("Cancellation successful!");
                        } else {
                            System.out.println("Cancellation failed!");
                        }
                        break;

                    case 0:
                        System.out.println("Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } while (choice != 0);

            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
