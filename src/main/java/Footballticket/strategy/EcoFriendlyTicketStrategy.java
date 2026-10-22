package Footballticket.strategy;

import Footballticket.model.*;
import Footballticket.util.TicketFilter;

import java.util.Comparator;
import java.util.List;

public class EcoFriendlyTicketStrategy implements TicketStrategy {

    private double ecoScore(Ticket ticket) {
        return ticket.carbonEmission() * 0.7
                + ticket.walkingDistanceKm() * 0.3;
    }

    @Override
    public Ticket selectTicket(
            List<Ticket> tickets,
            TicketRequest request) {

        return TicketFilter.eligible(tickets, request)
                .stream()
                .min(Comparator.comparingDouble(this::ecoScore))
                .orElseThrow();
    }

    @Override
    public String getStrategyName() {
        return "Eco Friendly";
    }

    @Override
    public String explain(Ticket ticket, TicketRequest request) {
        return "Carbon emission: " + ticket.carbonEmission()
                + ", eco score: " + ecoScore(ticket);
    }
}