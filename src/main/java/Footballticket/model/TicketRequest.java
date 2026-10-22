package Footballticket.model;

public record TicketRequest(
        TicketPriority priority,
        double maxBudget,
        int maxEntryMinutes,
        boolean ecoPreferred
) {
    public TicketRequest {
        if (priority == null
                || !Double.isFinite(maxBudget)
                || maxBudget < 0
                || maxEntryMinutes < 0) {
            throw new IllegalArgumentException("Invalid ticket request");
        }
    }
}
