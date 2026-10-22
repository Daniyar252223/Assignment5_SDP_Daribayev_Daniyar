package Footballticket.strategy;

import Footballticket.model.*;
import Footballticket.util.TicketFilter;

import java.util.Comparator;
import java.util.List;

public class FastestEntryStrategy implements TicketStrategy {

    private double effectiveTime(Ticket ticket) {
        return ticket.entryMinutes()
                + ticket.walkingDistanceKm() * 10;
    }

    @Override
    public Ticket selectTicket(
            List<Ticket> tickets,
            TicketRequest request) {

        return TicketFilter.eligible(tickets, request)
                .stream()
                .min(Comparator.comparingDouble(this::effectiveTime)
                        .thenComparing(Ticket::id))
                .orElseThrow();
    }

    @Override
    public String getStrategyName() {
        return "Fastest Entry";
    }

    @Override
    public String explain(Ticket ticket, TicketRequest request) {
        return "Entry time: " + ticket.entryMinutes()
                + " min, walking distance: "
                + ticket.walkingDistanceKm()
                + " km, effective time: "
                + effectiveTime(ticket) + " min";
    }
}