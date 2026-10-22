package Footballticket.planner;

import Footballticket.model.*;
import Footballticket.strategy.*;

import java.util.List;

public class StrategyComparison {

    public static void compare(
            List<TicketStrategy> strategies,
            List<Ticket> tickets,
            TicketRequest request) {

        for (TicketStrategy strategy : strategies) {
            System.out.println("\nStrategy: "
                    + strategy.getStrategyName());

            try {
                Ticket ticket = strategy.selectTicket(tickets, request);

                System.out.println("Selected: " + ticket.id());
                System.out.println("Section: " + ticket.section());
                System.out.println("Price: " + ticket.price() + " KZT");
                System.out.println("Reason: "
                        + strategy.explain(ticket, request));

            } catch (RuntimeException exception) {
                System.out.println("Failed: "
                        + exception.getMessage());
            }
        }
    }
}