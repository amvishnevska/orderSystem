package infrastructure;

import domain.PaymentStrategy;

public class PaypalPayment implements PaymentStrategy {
    private final String email;

    public PaypalPayment(String email) {
        this.email = email;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.printf("[Payment] Processing $%.2f via PayPal account %s%n", amount, email);
        return true;
    }
}