package Footballticket.strategy;

import Footballticket.model.*;
import java.util.List;

public interface TicketStrategy {

    Ticket selectTicket(
            List<Ticket> tickets,
            TicketRequest request
    );

    String getStrategyName();

    String explain(Ticket ticket, TicketRequest request);
}
