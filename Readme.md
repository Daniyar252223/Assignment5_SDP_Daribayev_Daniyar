
# Assignment 5 — Football Match Ticket Booking System

**Student:** Daniyar Daribayev  
**Group:** SE2516

**Design Pattern:** Strategy Pattern

## Project Description

This project is a football match ticket selection system using the Strategy Design Pattern.

The system helps customers choose suitable football tickets based on price, entry time, walking distance, environmental impact, and comfort.

Different strategies can be selected automatically or changed at runtime without modifying the main ticket planning logic.

## Implemented Strategies

1. **FastestEntryStrategy**
    - Minimizes effective entry time.
    - Considers entry time and walking distance.

2. **CheapestTicketStrategy**
    - Selects the ticket with the lowest total cost.
    - Includes service fees and priority surcharges.

3. **EcoFriendlyTicketStrategy**
    - Minimizes environmental impact.
    - Uses carbon emission and walking distance.

4. **BalancedTicketStrategy**
    - Considers time, cost, distance, and emissions.
    - Uses configurable weights:
        - Time: 0.40
        - Cost: 0.30
        - Distance: 0.10
        - Emissions: 0.20

5. **PremiumComfortStrategy**
    - Selects the most comfortable available ticket.
    - Uses comfort scores from 1 to 10.

## Architecture

The project follows the Strategy Design Pattern.

- **Context:** TicketPlanner
- **Strategy Interface:** TicketStrategy
- **Concrete Strategies:**
    - FastestEntryStrategy
    - CheapestTicketStrategy
    - EcoFriendlyTicketStrategy
    - BalancedTicketStrategy
    - PremiumComfortStrategy
- **Automatic Strategy Selection:** StrategySelector
- **Domain Models:**
    - Ticket
    - TicketRequest
    - TicketPriority
- **Constraint Filtering:** TicketFilter
- **Custom Exception:** NoSuitableTicketException
- **Strategy Comparison:** StrategyComparison

## Strategy Pattern Questions

### 1. What problem does Strategy solve?

The Strategy Pattern allows the system to use different ticket-selection algorithms without changing the TicketPlanner class.

### 2. Where is the Context?

TicketPlanner is the Context. It stores a TicketStrategy object and delegates ticket selection to it.

### 3. Where is the Strategy interface?

TicketStrategy is the common interface implemented by all ticket-selection algorithms.

### 4. What are the Concrete Strategies?

The Concrete Strategies are:

- FastestEntryStrategy
- CheapestTicketStrategy
- EcoFriendlyTicketStrategy
- BalancedTicketStrategy
- PremiumComfortStrategy

### 5. Where is runtime strategy switching demonstrated?

Runtime switching is demonstrated in Main.java using the setStrategy() method.

The same TicketPlanner object can use different strategies during program execution.

### 6. Why Strategy instead of simple if/else?

Each algorithm is implemented in a separate class.

This makes the system easier to maintain, test, and extend without creating large conditional statements.

### 7. How can a new Strategy be added?

A new class can be created by implementing the TicketStrategy interface.

The new strategy can then be registered in StrategySelector for automatic selection.

The TicketPlanner class does not need to be modified.

### 8. What is the difference between Strategy and Bridge?

Strategy changes the algorithm used to solve a problem.

Bridge separates an abstraction from its implementation so both can vary independently.

In this project, Strategy allows different ticket-selection algorithms.

In the previous Bridge Pattern assignment, different notification types were separated from message-sending providers.

### 9. Which SOLID principles are demonstrated?

**SRP — Single Responsibility Principle**

Each class has a specific responsibility.

**OCP — Open/Closed Principle**

New strategies can be added without modifying TicketPlanner.

**DIP — Dependency Inversion Principle**

TicketPlanner depends on the TicketStrategy interface rather than concrete strategy classes.

### 10. What limitation does the current architecture have?

The system uses demonstration data instead of a real football stadium ticket database.

Environmental scores are simulated.

The application does not process real ticket payments or reservations.

## Hard Constraints

The system rejects tickets that:

- Are unavailable.
- Exceed the customer's maximum budget.
- Exceed the maximum allowed entry time.

If no suitable ticket exists, the system throws NoSuitableTicketException.

## Runtime Strategy Switching

The system can switch between different strategies during execution.

Example:

```java
planner.setStrategy(new FastestEntryStrategy());
planner.plan(tickets, request);

planner.setStrategy(new CheapestTicketStrategy());
planner.plan(tickets, request);

planner.setStrategy(new EcoFriendlyTicketStrategy());
planner.plan(tickets, request);
```

## Automatic Strategy Selection

The StrategySelector automatically selects a strategy based on the customer's priority.

| Priority | Selected Strategy |
|----------|-------------------|
| URGENT | FastestEntryStrategy |
| ECONOMY | CheapestTicketStrategy |
| ECO | EcoFriendlyTicketStrategy |
| STANDARD | BalancedTicketStrategy |
| PREMIUM | PremiumComfortStrategy |

## Example Console Output

```text
Automatic selection: T4

RUNTIME STRATEGY SWITCHING
Fastest Entry -> T2 (VIP West)
Cheapest Ticket -> T5 (Upper Tier)
Eco Friendly -> T4 (South Stand)
Balanced -> T4 (South Stand)
Premium Comfort -> T2 (VIP West)

DYNAMIC REQUESTS
URGENT -> T2
ECONOMY -> T5
ECO -> T4
STANDARD -> T4
PREMIUM -> T2
```

## Testing

JUnit 5 is used for automated testing.

The project includes tests for:

- Fastest strategy
- Cheapest strategy
- Eco-friendly strategy
- Balanced strategy
- Premium strategy
- Runtime strategy switching
- Automatic strategy selection
- Budget restrictions
- Entry-time restrictions
- Unavailable tickets
- No suitable tickets
- Empty ticket lists
- Invalid weights
- Negative values
- Strategy comparison
- Boundary conditions

To run the tests:

```bash
mvn test
```

## How to Run

1. Open the project in IntelliJ IDEA.
2. Make sure Java 17 or higher is installed.
3. Reload the Maven project.
4. Open Main.java.
5. Click Run.
6. Check the console output.
7. Run TicketStrategyTest.java to execute the tests.

## UML Diagrams

The project uses two UML diagrams:

### UML Class Diagram

Shows the relationship between:

- TicketPlanner
- TicketStrategy
- Concrete Strategies
- StrategySelector
- Ticket
- TicketRequest

### UML Sequence Diagram

Shows the interaction between:

1. Client
2. StrategySelector
3. TicketPlanner
4. Concrete Strategy
5. TicketFilter
6. Selected Ticket

## Conclusion

This project demonstrates the Strategy Design Pattern through a football ticket selection system.

The application supports multiple interchangeable algorithms, automatic strategy selection, runtime switching, constraint filtering, and strategy comparison.

The architecture follows important SOLID principles and allows new strategies to be added with minimal changes to existing code.
