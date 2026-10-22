package Footballticket.strategy;

import Footballticket.model.*;
import Footballticket.util.TicketFilter;

import java.util.Comparator;
import java.util.List;

public class CheapestTicketStrategy implements TicketStrategy {

    private double totalCost(Ticket ticket, TicketRequest request) {
        double serviceFee = ticket.price() * 0.05;
        double priorityFee =
                request.priority() == TicketPriority.URGENT ? 500 : 0;

        return ticket.price() + serviceFee + priorityFee;
    }

    @Override
    public Ticket selectTicket(
            List<Ticket> tickets,
            TicketRequest request) {

        List<Ticket> eligible = TicketFilter.eligible(tickets, request)
                .stream()
                .filter(t -> totalCost(t, request) <= request.maxBudget())
                .toList();

        if (eligible.isEmpty()) {
            throw new Footballticket.exception
                    .NoSuitableTicketException("No affordable ticket");
        }

        return eligible.stream()
                .min(Comparator.comparingDouble(
                        t -> totalCost(t, request)))
                .orElseThrow();
    }

    @Override
    public String getStrategyName() {
        return "Cheapest Ticket";
    }

    @Override
    public String explain(Ticket ticket, TicketRequest request) {
        return "Base price: " + ticket.price()
                + " KZT, total cost with fees: "
                + totalCost(ticket, request) + " KZT";
    }
}
