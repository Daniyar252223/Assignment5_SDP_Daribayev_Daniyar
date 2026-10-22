package Footballticket.planner;

import Footballticket.model.*;
import Footballticket.strategy.*;

import java.util.List;
import java.util.Objects;

public class TicketPlanner {

    private TicketStrategy strategy;

    public TicketPlanner(TicketStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(TicketStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }

    public Ticket plan(
            List<Ticket> tickets,
            TicketRequest request) {

        return strategy.selectTicket(tickets, request);
    }

    public TicketStrategy getStrategy() {
        return strategy;
    }
}