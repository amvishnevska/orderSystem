package domain;

import java.util.List;
import java.util.ArrayList;

public class Order {
    private final String orderId;
    private final List<OrderItem> items = new ArrayList<>();
    private final List<OrderObserver> observers = new ArrayList<>();
    private PaymentStrategy paymentStrategy;
    private OrderStatus status;

    public Order(String orderId) {
        this.orderId = orderId;
        this.status = OrderStatus.NEW;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void attachObserver(OrderObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.update(this);
        }
    }

    public double calculateTotal() {
        return items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    public void process() {
        if (items.isEmpty()) {
            throw new IllegalStateException("Cannot process an empty order.");
        }
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy must be set before processing.");
        }

        double total = calculateTotal();
        boolean paymentSuccessful = paymentStrategy.processPayment(total);

        if (paymentSuccessful) {
            this.status = OrderStatus.PAID;
            notifyObservers();
        } else {
            this.status = OrderStatus.CANCELLED;
        }
    }

    public String getOrderId() { return orderId; }
    public OrderStatus getStatus() { return status; }
    public List<OrderItem> getItems() { return items; }
}