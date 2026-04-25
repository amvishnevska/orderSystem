package domain;

public class OrderBuilder {
    private final Order order;

    public OrderBuilder(String orderId) {
        this.order = new Order(orderId);
    }

    public OrderBuilder addProduct(Product product, int quantity) {
        this.order.addItem(new OrderItem(product, quantity));
        return this;
    }

    public OrderBuilder setPaymentMethod(PaymentStrategy strategy) {
        this.order.setPaymentStrategy(strategy);
        return this;
    }

    public OrderBuilder addObserver(OrderObserver observer) {
        this.order.attachObserver(observer);
        return this;
    }

    public Order build() {
        return this.order;
    }
}