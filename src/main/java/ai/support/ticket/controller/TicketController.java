package ai.support.ticket.controller;

import ai.support.ticket.model.SupportTicket;
import ai.support.ticket.service.TicketCategorizerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketCategorizerService categorizerService;
    public TicketController(TicketCategorizerService categorizerService) {
        this.categorizerService = categorizerService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<SupportTicket> createAndAnalyzeTicket(@RequestBody String rawDescription) {
        return ResponseEntity.ok(categorizerService.processAndSaveTicket(rawDescription));
    }
}

