package Footballticket.model;

public record Ticket(
        String id,
        String match,
        String section,
        double price,
        int entryMinutes,
        double walkingDistanceKm,
        double carbonEmission,
        int comfortScore,
        boolean available
) {
    public Ticket {
        if (id == null || id.isBlank()
                || match == null || match.isBlank()
                || section == null || section.isBlank()) {
            throw new IllegalArgumentException("Ticket details are required");
        }

        if (!Double.isFinite(price) || price < 0
                || entryMinutes < 0
                || !Double.isFinite(walkingDistanceKm)
                || walkingDistanceKm < 0
                || !Double.isFinite(carbonEmission)
                || carbonEmission < 0
                || comfortScore < 1 || comfortScore > 10) {
            throw new IllegalArgumentException("Invalid ticket data");
        }
    }
}
