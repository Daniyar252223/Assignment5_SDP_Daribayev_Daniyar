package Footballticket.util;

import Footballticket.exception.NoSuitableTicketException;
import Footballticket.model.*;

import java.util.List;

public final class TicketFilter {

    private TicketFilter() {}

    public static List<Ticket> eligible(
            List<Ticket> tickets,
            TicketRequest request) {

        if (tickets == null || request == null) {
            throw new IllegalArgumentException("Missing input");
        }

        List<Ticket> result = tickets.stream()
                .filter(t -> t != null)
                .filter(Ticket::available)
                .filter(t -> t.price() <= request.maxBudget())
                .filter(t -> t.entryMinutes() <= request.maxEntryMinutes())
                .toList();

        if (result.isEmpty()) {
            throw new NoSuitableTicketException(
                    "No ticket satisfies the request constraints");
        }

        return result;
    }
}