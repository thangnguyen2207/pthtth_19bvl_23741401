package lab6.bai9;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            AuctionInterface auction = (AuctionInterface) registry.lookup("AuctionService");

            Scanner sc = new Scanner(System.in);

            System.out.print("Enter your name: ");
            String username = sc.nextLine();

            int role;

            do {
                System.out.println("\n===== ONLINE AUCTION SYSTEM =====");
                System.out.println("1. Seller");
                System.out.println("2. Bidder");
                System.out.println("0. Exit");
                System.out.print("Choose role: ");

                role = sc.nextInt();
                sc.nextLine();

                switch (role) {
                    case 1:
                        System.out.print("Enter product name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter starting price: ");
                        double startPrice = sc.nextDouble();
                        sc.nextLine();

                        if (auction.addProduct(name, startPrice)) {
                            System.out.println("Product added successfully!");
                        } else {
                            System.out.println("Failed to add product!");
                        }
                        break;

                    case 2:
                        int choice;

                        do {
                            System.out.println("\n===== BIDDER MENU =====");
                            System.out.println("1. View Products");
                            System.out.println("2. Place Bid");
                            System.out.println("0. Back");
                            System.out.print("Enter choice: ");

                            choice = sc.nextInt();
                            sc.nextLine();

                            switch (choice) {
                                case 1:
                                    System.out.println("\n===== PRODUCT LIST =====");
                                    System.out.println(auction.getProducts());
                                    break;

                                case 2:
                                    System.out.print("Enter product ID: ");
                                    int productId = sc.nextInt();

                                    System.out.print("Enter bid price: ");
                                    double bidPrice = sc.nextDouble();
                                    sc.nextLine();

                                    if (auction.placeBid(productId, username, bidPrice)) {
                                        System.out.println("Your bid is successful!");
                                        System.out.println("You are the highest bidder!");
                                    } else {
                                        System.out.println("Bid failed! Please enter a price higher than the current price.");
                                    }
                                    break;

                                case 0:
                                    System.out.println("Back to main menu.");
                                    break;

                                default:
                                    System.out.println("Invalid choice!");
                            }

                        } while (choice != 0);
                        break;

                    case 0:
                        System.out.println("Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } while (role != 0);

            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
