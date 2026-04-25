package infrastructure;

import domain.Order;
import domain.OrderItem;
import domain.OrderObserver;
import domain.OrderStatus;

public class InventoryManager implements OrderObserver {
    @Override
    public void update(Order order) {
        if (order.getStatus() == OrderStatus.PAID) {
            System.out.printf("[Inventory] Deducting stock for Order %s%n", order.getOrderId());
            for (OrderItem item : order.getItems()) {
                System.out.printf("  -> Deducted %d units of %s%n", item.getQuantity(), item.getProduct().getName());
            }
        }
    }
}