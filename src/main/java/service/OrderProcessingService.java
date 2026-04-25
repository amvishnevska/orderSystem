package service;

import domain.Order;
import infrastructure.OrderRepository;

public class OrderProcessingService {
    private final OrderRepository repository;

    public OrderProcessingService(OrderRepository repository) {
        this.repository = repository;
    }

    public void checkout(Order order) {
        System.out.println("\n--- Starting Checkout for Order: " + order.getOrderId() + " ---");

        order.process();

        repository.save(order);

        System.out.println("--- Checkout Complete. Final Status: " + order.getStatus() + " ---\n");
    }
}