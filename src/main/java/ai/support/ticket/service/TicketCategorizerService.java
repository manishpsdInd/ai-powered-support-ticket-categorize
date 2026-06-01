package ai.support.ticket.service;

import ai.support.ticket.dto.TicketAnalysisResult;
import ai.support.ticket.model.SupportTicket;
import ai.support.ticket.repository.SupportTicketRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class TicketCategorizerService{

    private final ChatClient chatClient;
    private final SupportTicketRepository ticketRepository;

    public TicketCategorizerService(ChatClient.Builder chatClientBuilder, SupportTicketRepository ticketRepository) {         this.chatClient = chatClientBuilder.build();
        this.ticketRepository = ticketRepository;
    }

    public SupportTicket processAndSaveTicket(String rawDescription) {
        String systemPrompt = """
            You are an expert customer support routing assistant.
            Analyze the incoming raw customer issue and extract:
            Category: Must strictly be one of these: [BILLING, TECHNICAL_ISSUE, ACCOUNT_ACCESS, OTHER].
            Priority: Must strictly be one of these: [LOW, MEDIUM, HIGH, CRITICAL].
            Summary: A single, clean, concise sentence summarizing the main problem.
            """;
        TicketAnalysisResult analysis = this.chatClient.prompt()
                .system(systemPrompt)
                .user(rawDescription)
                .call()
                .entity(TicketAnalysisResult.class);

        SupportTicket ticket = new SupportTicket();
        ticket.setRawDescription(rawDescription);
        ticket.setCategory(analysis.category());
        ticket.setPriority(analysis.priority());
        ticket.setCleanSummary(analysis.summary());

        return ticketRepository.save(ticket);
    }
}

