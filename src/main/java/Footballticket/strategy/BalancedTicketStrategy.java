package Footballticket.strategy;


import Footballticket.model.*;
import Footballticket.util.TicketFilter;

import java.util.Comparator;
import java.util.List;

public class BalancedTicketStrategy implements TicketStrategy {

    private final double timeWeight;
    private final double costWeight;
    private final double distanceWeight;
    private final double emissionWeight;

    public BalancedTicketStrategy(
            double timeWeight,
            double costWeight,
            double distanceWeight,
            double emissionWeight) {

        double sum = timeWeight + costWeight
                + distanceWeight + emissionWeight;

        if (!Double.isFinite(sum)
                || Math.abs(sum - 1.0) > 0.000001
                || timeWeight < 0 || costWeight < 0
                || distanceWeight < 0 || emissionWeight < 0) {
            throw new IllegalArgumentException(
                    "Weights must be nonnegative and sum to 1.0");
        }

        this.timeWeight = timeWeight;
        this.costWeight = costWeight;
        this.distanceWeight = distanceWeight;
        this.emissionWeight = emissionWeight;
    }

    private double normalize(double value, double maximum) {
        return maximum == 0 ? 0 : value / maximum;
    }

    public double score(Ticket ticket, List<Ticket> candidates) {
        double maxTime = candidates.stream()
                .mapToDouble(Ticket::entryMinutes).max().orElse(0);
        double maxCost = candidates.stream()
                .mapToDouble(Ticket::price).max().orElse(0);
        double maxDistance = candidates.stream()
                .mapToDouble(Ticket::walkingDistanceKm).max().orElse(0);
        double maxEmission = candidates.stream()
                .mapToDouble(Ticket::carbonEmission).max().orElse(0);

        return normalize(ticket.entryMinutes(), maxTime) * timeWeight
                + normalize(ticket.price(), maxCost) * costWeight
                + normalize(ticket.walkingDistanceKm(), maxDistance)
                * distanceWeight
                + normalize(ticket.carbonEmission(), maxEmission)
                * emissionWeight;
    }

    @Override
    public Ticket selectTicket(
            List<Ticket> tickets,
            TicketRequest request) {

        List<Ticket> eligible = TicketFilter.eligible(tickets, request);

        return eligible.stream()
                .min(Comparator.comparingDouble(
                        t -> score(t, eligible)))
                .orElseThrow();
    }

    @Override
    public String getStrategyName() {
        return "Balanced";
    }

    @Override
    public String explain(Ticket ticket, TicketRequest request) {
        return "Selected using normalized time, cost, "
                + "distance and emission with configurable weights.";
    }
}