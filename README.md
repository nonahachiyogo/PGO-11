# Object-Oriented Equipment Reservation System (MediaLab)

A fully working, console-based Java application designed to manage equipment reservations for a university MediaLab. This project demonstrates core Object-Oriented Programming (OOP) design patterns and clean code principles.

---

## 🛠️ Project Structure and Class Responsibilities

The system is split into distinct classes and interfaces to fulfill the **Separation of Responsibilities** principle, keeping the `Main` class clean and focused only on user interaction.

### Core Classes
* **`Main`**: Acts as the user interface controller. It handles the menu loop, captures console inputs, catches input formatting exceptions, and routes choices to the service layer.
* **`Student`**: A domain model representing a student. It encapsulates student attributes (`id`, `fullName`, `groupName`, `loyaltyPoints`) and provides methods to mutate its internal state (e.g., adding loyalty points).
* **`Equipment`**: An abstract base class representing an inventory item. It contains shared data fields and forces subclasses to implement specific pricing and feature disclosure strategies.
* **`LaptopSet`**: A concrete subclass of `Equipment`. It adds properties for RAM capacity and docking station presence, and implements its own polymorphic daily price calculation rule.
* **`CameraKit`**: A concrete subclass of `Equipment`. It tracks the number of lenses and tripod presence, calculating daily prices based on photography accessory criteria.
* **`Reservation`**: A transactional entity that binds a `Student` object and an `Equipment` object together. It manages the duration, active status state, and triggers total cost computation via an injected discount strategy.
* **`ReservationService`**: The core business logic engine. It manages collections (`List<T>`) of students, equipment, and reservations. It handles the core verification rules, state mutations (booking/returning), and data aggregations for reports.

### Data Types
* **`ReservationStatus` (Enum)**: Explicitly tracks valid transactional lifecycle states: `ACTIVE`, `RETURNED`, or `CANCELLED`.

---

## 🧩 Interfaces and Implementations

The application uses custom interfaces to decoupling behaviors from data models:

1.  **`Displayable`**
    * **Purpose**: Provides a unified method for any object to generate clean, descriptive text representations for terminal display without overriding `toString()` in a way that limits future formatting.
    * **Implementations**: Built directly into `Equipment` (inherited by `LaptopSet` and `CameraKit`) and `Reservation`.
2.  **`DiscountPolicy`**
    * **Purpose**: Implements the **Strategy Design Pattern**. It extracts financial discount rules out of core transaction records, making the system open to adding new discount logic (e.g., Seasonal, Group discounts) without editing the `Reservation` class code.
    * **Implementations**: `LoyaltyDiscountPolicy`, which grants a 10% markdown to any student who has accumulated 100+ loyalty points.

---

## 🔄 Polymorphism in Action

The primary example of polymorphism occurs during pricing calculations inside the `Reservation` cost compilation step:

```java
double baseCost = equipment.calculateDailyPrice() * days;
