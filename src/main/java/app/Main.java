package app;

import domain.Order;
import domain.OrderBuilder;
import domain.OrderObserver;
import domain.Product;
import infrastructure.*;
import service.OrderProcessingService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Dependency initialisation
        OrderRepository repository = new InMemoryOrderRepository();
        OrderProcessingService orderService = new OrderProcessingService(repository);

        OrderObserver emailNotifier = new EmailNotifier();
        OrderObserver inventoryManager = new InventoryManager();

        // DB of products
        Product laptop = new Product("P1", "Developer Laptop", 1200.00);
        Product mouse = new Product("P2", "Wireless Mouse", 50.00);

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Welcome to the Interactive Order System CLI ===");

        // Menu
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Create a new order");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            if ("2".equals(choice)) {
                System.out.println("Exiting system. Goodbye!");
                break;
            }

            if ("1".equals(choice)) {
                // Builder for order with random ID
                String orderId = "ORD-" + System.currentTimeMillis() % 10000;
                OrderBuilder builder = new OrderBuilder(orderId)
                        .addObserver(emailNotifier)
                        .addObserver(inventoryManager);

                System.out.println("\n--- Order " + orderId + " ---");

                // Adding products
                while (true) {
                    System.out.println("Available products:");
                    System.out.println("1. " + laptop.getName() + " ($" + laptop.getPrice() + ")");
                    System.out.println("2. " + mouse.getName() + " ($" + mouse.getPrice() + ")");
                    System.out.println("0. Finish adding products");
                    System.out.print("Select product to add: ");

                    String productChoice = scanner.nextLine();

                    if ("0".equals(productChoice)) break;

                    if ("1".equals(productChoice)) {
                        builder.addProduct(laptop, 1);
                        System.out.println("Laptop added to cart.\n");
                    } else if ("2".equals(productChoice)) {
                        builder.addProduct(mouse, 1);
                        System.out.println("Mouse added to cart.\n");
                    } else {
                        System.out.println("Invalid choice. Try again.\n");
                    }
                }

                // Payment methods
                System.out.println("Select payment method:");
                System.out.println("1. Credit Card");
                System.out.println("2. PayPal");
                System.out.print("Choice: ");
                String paymentChoice = scanner.nextLine();

                if ("1".equals(paymentChoice)) {
                    builder.setPaymentMethod(new CreditCardPayment("1111-2222-3333-4444"));
                } else {
                    builder.setPaymentMethod(new PaypalPayment("student@university.edu"));
                }

                // Collecting and processing order
                Order finalOrder = builder.build();

                try {
                    orderService.checkout(finalOrder);
                } catch (IllegalStateException e) {
                    System.out.println("Error processing order: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid option. Please enter 1 or 2.");
            }
        }

        scanner.close();
    }
}