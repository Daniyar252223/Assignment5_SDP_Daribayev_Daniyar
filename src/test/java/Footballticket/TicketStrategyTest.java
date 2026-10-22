package Footballticket;

import Footballticket.exception.NoSuitableTicketException;
import Footballticket.model.*;
import Footballticket.planner.*;
import Footballticket.strategy.*;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TicketStrategyTest {

    private final List<Ticket> tickets = List.of(
            new Ticket("A", "Match", "North", 10000,
                    20, 1.0, 3.0, 5, true),
            new Ticket("B", "Match", "VIP", 20000,
                    5, 0.2, 2.0, 10, true),
            new Ticket("C", "Match", "Eco", 7000,
                    30, 0.5, 0.5, 7, true),
            new Ticket("D", "Match", "Closed", 3000,
                    2, 0.1, 0.1, 10, false)
    );

    private TicketRequest request() {
        return new TicketRequest(
                TicketPriority.STANDARD, 30000, 40, false);
    }

    @Test
    void fastestStrategy() {
        assertEquals("B",
                new FastestEntryStrategy()
                        .selectTicket(tickets, request()).id());
    }

    @Test
    void cheapestStrategy() {
        assertEquals("C",
                new CheapestTicketStrategy()
                        .selectTicket(tickets, request()).id());
    }

    @Test
    void ecoStrategy() {
        assertEquals("C",
                new EcoFriendlyTicketStrategy()
                        .selectTicket(tickets, request()).id());
    }

    @Test
    void balancedStrategy() {
        Ticket selected = new BalancedTicketStrategy(
                0.4, 0.3, 0.1, 0.2)
                .selectTicket(tickets, request());

        assertTrue(List.of("A", "B", "C").contains(selected.id()));
    }

    @Test
    void premiumStrategy() {
        assertEquals("B",
                new PremiumComfortStrategy()
                        .selectTicket(tickets, request()).id());
    }

    @Test
    void runtimeSwitching() {
        TicketPlanner planner =
                new TicketPlanner(new CheapestTicketStrategy());

        assertEquals("C", planner.plan(tickets, request()).id());

        planner.setStrategy(new FastestEntryStrategy());

        assertEquals("B", planner.plan(tickets, request()).id());
    }

    @Test
    void automaticSelection() {
        StrategySelector selector = new StrategySelector();

        assertInstanceOf(FastestEntryStrategy.class,
                selector.select(new TicketRequest(
                        TicketPriority.URGENT, 30000, 40, false)));
    }

    @Test
    void budgetRestriction() {
        TicketRequest limited = new TicketRequest(
                TicketPriority.ECONOMY, 8000, 40, false);

        assertEquals("C", new CheapestTicketStrategy()
                .selectTicket(tickets, limited).id());
    }

    @Test
    void deadlineRestriction() {
        TicketRequest limited = new TicketRequest(
                TicketPriority.URGENT, 30000, 10, false);

        assertEquals("B", new FastestEntryStrategy()
                .selectTicket(tickets, limited).id());
    }

    @Test
    void unavailableTicketRejected() {
        Ticket selected = new CheapestTicketStrategy()
                .selectTicket(tickets, request());

        assertNotEquals("D", selected.id());
    }

    @Test
    void noSuitableTicket() {
        TicketRequest limited = new TicketRequest(
                TicketPriority.ECONOMY, 1000, 10, false);

        assertThrows(NoSuitableTicketException.class,
                () -> new CheapestTicketStrategy()
                        .selectTicket(tickets, limited));
    }

    @Test
    void emptyTicketList() {
        assertThrows(NoSuitableTicketException.class,
                () -> new FastestEntryStrategy()
                        .selectTicket(List.of(), request()));
    }

    @Test
    void invalidWeights() {
        assertThrows(IllegalArgumentException.class,
                () -> new BalancedTicketStrategy(
                        0.5, 0.5, 0.5, 0.5));
    }

    @Test
    void negativeWeight() {
        assertThrows(IllegalArgumentException.class,
                () -> new BalancedTicketStrategy(
                        -0.1, 0.5, 0.3, 0.3));
    }

    @Test
    void negativeTicketPrice() {
        assertThrows(IllegalArgumentException.class,
                () -> new Ticket("X", "Match", "North",
                        -100, 10, 0.5, 1, 5, true));
    }

    @Test
    void compareTwoStrategies() {
        Ticket cheapest = new CheapestTicketStrategy()
                .selectTicket(tickets, request());

        Ticket fastest = new FastestEntryStrategy()
                .selectTicket(tickets, request());

        assertNotEquals(cheapest.id(), fastest.id());
    }

    @Test
    void boundaryBudget() {
        TicketRequest exactBudget = new TicketRequest(
                TicketPriority.STANDARD, 7000, 40, false);

        assertEquals("C", new FastestEntryStrategy()
                .selectTicket(tickets, exactBudget).id());
    }
}
