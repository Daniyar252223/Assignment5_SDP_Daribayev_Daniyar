package Footballticket;

import Footballticket.model.*;
import Footballticket.planner.*;
import Footballticket.strategy.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Ticket> tickets = List.of(
                new Ticket("T1", "Real Madrid vs Barcelona",
                        "North Stand", 12000, 15, 0.8, 2.1, 6, true),

                new Ticket("T2", "Real Madrid vs Barcelona",
                        "VIP West", 35000, 5, 0.2, 1.5, 10, true),

                new Ticket("T3", "Real Madrid vs Barcelona",
                        "East Stand", 8000, 25, 1.2, 2.8, 5, true),

                new Ticket("T4", "Real Madrid vs Barcelona",
                        "South Stand", 10000, 12, 0.4, 0.8, 7, true),

                new Ticket("T5", "Real Madrid vs Barcelona",
                        "Upper Tier", 5000, 35, 1.5, 1.2, 4, true),

                new Ticket("T6", "Real Madrid vs Barcelona",
                        "Central Stand", 18000, 10, 0.3, 1.8, 9, true),

                new Ticket("T7", "Real Madrid vs Barcelona",
                        "Family Zone", 9000, 20, 0.6, 1.0, 8, true),

                new Ticket("T8", "Real Madrid vs Barcelona",
                        "Premium Lounge", 45000, 4, 0.1, 3.0, 10, false)
        );

        TicketRequest request = new TicketRequest(
                TicketPriority.STANDARD,
                40000,
                40,
                false
        );

        StrategySelector selector = new StrategySelector();

        TicketPlanner planner = new TicketPlanner(
                selector.select(request));

        System.out.println("FOOTBALL MATCH TICKET BOOKING SYSTEM");

        Ticket automatic = planner.plan(tickets, request);
        System.out.println("\nAutomatic selection: " + automatic.id());

        System.out.println("\nRUNTIME STRATEGY SWITCHING");

        List<TicketStrategy> strategies = List.of(
                new FastestEntryStrategy(),
                new CheapestTicketStrategy(),
                new EcoFriendlyTicketStrategy(),
                new BalancedTicketStrategy(0.4, 0.3, 0.1, 0.2),
                new PremiumComfortStrategy()
        );

        for (TicketStrategy strategy : strategies) {
            planner.setStrategy(strategy);
            Ticket selected = planner.plan(tickets, request);

            System.out.println(strategy.getStrategyName()
                    + " -> " + selected.id()
                    + " (" + selected.section() + ")");
        }

        System.out.println("\nSTRATEGY COMPARISON");
        StrategyComparison.compare(strategies, tickets, request);

        System.out.println("\nDYNAMIC REQUESTS");

        for (TicketPriority priority : TicketPriority.values()) {
            TicketRequest dynamicRequest =
                    new TicketRequest(priority, 40000, 40,
                            priority == TicketPriority.ECO);

            planner.setStrategy(selector.select(dynamicRequest));

            Ticket selected = planner.plan(tickets, dynamicRequest);

            System.out.println(priority + " -> " + selected.id());
        }
    }
}