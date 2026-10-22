package Footballticket.strategy;

import Footballticket.model.*;
import Footballticket.util.TicketFilter;

import java.util.Comparator;
import java.util.List;

public class PremiumComfortStrategy implements TicketStrategy {

    @Override
    public Ticket selectTicket(
            List<Ticket> tickets,
            TicketRequest request) {

        return TicketFilter.eligible(tickets, request)
                .stream()
                .max(Comparator.comparingInt(Ticket::comfortScore)
                        .thenComparing(
                                Comparator.comparingDouble(
                                        Ticket::price).reversed()))
                .orElseThrow();
    }

    @Override
    public String getStrategyName() {
        return "Premium Comfort";
    }

    @Override
    public String explain(Ticket ticket, TicketRequest request) {
        return "Comfort score: " + ticket.comfortScore()
                + "/10, price: " + ticket.price() + " KZT";
    }
}