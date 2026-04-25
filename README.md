# Order Management System

## System Description
This is an order management application project which is written in Java. The system models a real-world purchasing process: from assembling a shopping cart with items to processing a payment and executing post-processing actions (notifying the customer, deducting inventory).

The project is developed with a focus on clean code structure and adherence to **OOP** and **SOLID** principles. The application is highly extensible: adding new payment methods or new side-effects for a successful order does not require modifying the existing business logic.

---

## Architecture
The project is logically divided into three distinct layers to enforce the **Separation of Concerns** principle:

1. **Domain (`com.ordersystem.domain`)**
   - **Purpose:** Core business logic. Contains the main entities (`Order`, `Product`, `OrderItem`) and abstractions (interfaces for design patterns).
   - **Rule:** This layer knows absolutely nothing about the outside world (databases, specific payment gateways, external APIs).

2. **Infrastructure (`com.ordersystem.infrastructure`)**
   - **Purpose:** Implementation details. Contains concrete classes for payment processing (`CreditCardPayment`, `PaypalPayment`), concrete observers (`EmailNotifier`, `InventoryManager`), and a simulated data storage (`InMemoryOrderRepository`).
   - **Rule:** This layer depends on the `Domain` layer and implements its interfaces.

3. **Service & Application (`com.ordersystem.service`, `com.ordersystem.app`)**
   - **Purpose:** Orchestration. `OrderProcessingService` manages the checkout workflow, bridging the domain logic with repository persistence (following the Dependency Inversion Principle). The `Main` class serves as the entry point, wiring all dependencies together.

---

## Design Patterns Used
The project implements 3 classic GoF design patterns, each solving a specific architectural challenge:

### 1. Strategy Pattern
- **Where:** `PaymentStrategy` interface and its concrete implementations (`CreditCardPayment`, `PaypalPayment`).
- **Why:** Allows the payment algorithm to be selected dynamically at runtime. This adheres to the OCP: if we need to support Cryptocurrency payments in the future, we simply create a new class without ever touching the core `Order` class.

### 2. Observer Pattern
- **Where:** `OrderObserver` interface and its concrete implementations (`EmailNotifier`, `InventoryManager`).
- **Why:** Decouples post-purchase side-effects from the main order processing flow. The `Order` object knows nothing about email servers or warehouses, it simply "notifies" all its subscribers when its status changes to `PAID`. It allows for easy addition of new reactions (e.g., adding a `LoyaltyPointsManager`).

### 3. Builder Pattern
- **Where:** `OrderBuilder` class.
- **Why:** Simplifies the construction of a complex `Order` object. Instead of using multiple setter methods or a massive constructor, the Builder provides a clean **fluent API** for step-by-step configuration: adding products, setting the payment method, and attaching observers.

---

## How to Run
1. Clone this repository to your local machine.
2. Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).
3. Locate and run the `main` method inside the `com.ordersystem.app.Main` class.
4. Check the console output to observe the step-by-step processing of multiple orders using different strategies and observers.
