package lab6.bai7;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            AreaInterface area = (AreaInterface) registry.lookup("AreaService");

            Scanner sc = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\n===== AREA CALCULATOR =====");
                System.out.println("1. Rectangle Area");
                System.out.println("2. Circle Area");
                System.out.println("3. Triangle Area");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");

                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter width: ");
                        double width = sc.nextDouble();

                        System.out.print("Enter height: ");
                        double height = sc.nextDouble();

                        System.out.println("Rectangle Area: " + area.rectangleArea(width, height));
                        break;

                    case 2:
                        System.out.print("Enter radius: ");
                        double radius = sc.nextDouble();

                        System.out.println("Circle Area: " + area.circleArea(radius));
                        break;

                    case 3:
                        System.out.print("Enter base: ");
                        double base = sc.nextDouble();

                        System.out.print("Enter height: ");
                        double h = sc.nextDouble();

                        System.out.println("Triangle Area: " + area.triangleArea(base, h));
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
