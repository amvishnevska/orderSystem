package app;

import domain.Order;
import domain.OrderBuilder;
import domain.OrderObserver;
import domain.Product;
import infrastructure.*;
import service.OrderProcessingService;

public class Main {
    public static void main(String[] args) {
        OrderRepository repository = new InMemoryOrderRepository();
        OrderProcessingService orderService = new OrderProcessingService(repository);

        OrderObserver emailNotifier = new EmailNotifier();
        OrderObserver inventoryManager = new InventoryManager();

        Product laptop = new Product("P1", "Developer Laptop", 1200.00);
        Product mouse = new Product("P2", "Wireless Mouse", 50.00);

        Order order1 = new OrderBuilder("ORD-001")
                .addProduct(laptop, 1)
                .addProduct(mouse, 2)
                .setPaymentMethod(new CreditCardPayment("1234-5678-9012-3456"))
                .addObserver(emailNotifier)
                .addObserver(inventoryManager)
                .build();

        orderService.checkout(order1);

        Product keyboard = new Product("P3", "Mechanical Keyboard", 150.00);

        Order order2 = new OrderBuilder("ORD-002")
                .addProduct(keyboard, 1)
                .setPaymentMethod(new PaypalPayment("user@example.com"))
                .addObserver(emailNotifier) // Only sending email, no inventory check
                .build();

        orderService.checkout(order2);
    }
}