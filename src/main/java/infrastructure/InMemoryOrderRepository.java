package infrastructure;

import domain.Order;

import java.util.HashMap;
import java.util.Map;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<String, Order> database = new HashMap<>();

    @Override
    public void save(Order order) {
        database.put(order.getOrderId(), order);
        System.out.printf("[Database] Order %s saved to memory.%n", order.getOrderId());
    }
}