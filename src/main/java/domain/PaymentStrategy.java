package domain;

public interface PaymentStrategy {
    boolean processPayment(double amount);
}