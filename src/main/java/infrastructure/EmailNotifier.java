package infrastructure;

import domain.Order;
import domain.OrderObserver;

public class EmailNotifier implements OrderObserver {
    @Override
    public void update(Order order) {
        System.out.printf("[Email] Sending confirmation for Order %s. Status: %s%n", order.getOrderId(), order.getStatus());
    }
}