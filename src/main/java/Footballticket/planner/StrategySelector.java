package Footballticket.planner;

import Footballticket.model.*;
import Footballticket.strategy.*;

import java.util.EnumMap;
import java.util.Map;

public class StrategySelector {

    private final Map<TicketPriority, TicketStrategy> strategies =
            new EnumMap<>(TicketPriority.class);

    public StrategySelector() {
        strategies.put(TicketPriority.URGENT,
                new FastestEntryStrategy());

        strategies.put(TicketPriority.ECONOMY,
                new CheapestTicketStrategy());

        strategies.put(TicketPriority.ECO,
                new EcoFriendlyTicketStrategy());

        strategies.put(TicketPriority.STANDARD,
                new BalancedTicketStrategy(0.4, 0.3, 0.1, 0.2));

        strategies.put(TicketPriority.PREMIUM,
                new PremiumComfortStrategy());
    }

    public TicketStrategy select(TicketRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request is required");
        }

        TicketStrategy strategy = strategies.get(request.priority());

        if (strategy == null) {
            throw new IllegalArgumentException("Unknown priority");
        }

        return strategy;
    }

    public void register(
            TicketPriority priority,
            TicketStrategy strategy) {
        strategies.put(priority, strategy);
    }
}
