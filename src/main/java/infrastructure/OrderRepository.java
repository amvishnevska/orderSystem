package infrastructure;

import domain.Order;

public interface OrderRepository {
    void save(Order order);
}