package domain;

public interface OrderObserver {
    void update(Order order);
}