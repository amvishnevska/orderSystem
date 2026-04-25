package infrastructure;

import domain.PaymentStrategy;

public class CreditCardPayment implements PaymentStrategy {
    private final String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.printf("[Payment] Processing $%.2f via Credit Card ending in %s%n", amount, cardNumber.substring(cardNumber.length() - 4));
        return true;
    }
}
