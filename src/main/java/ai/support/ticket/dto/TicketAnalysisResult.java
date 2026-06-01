package ai.support.ticket.dto;

public record TicketAnalysisResult(
        String category,  // Strictly bounded to: BILLING, TECHNICAL_ISSUE, ACCOUNT_ACCESS, OTHER
        String priority,  // Strictly bounded to: LOW, MEDIUM, HIGH, CRITICAL
        String summary    // One clean, descriptive summary sentence
) {}
