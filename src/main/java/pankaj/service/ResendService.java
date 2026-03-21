package pankaj.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatusCode;
import reactor.core.publisher.Mono;

@Service
public class ResendService {

    private final WebClient webClient;

    public ResendService(@Value("${resend.api.key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.resend.com")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String sendEmail(String to, String subject, String html) {
    return webClient.post()
        .uri("/emails")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .bodyValue(new EmailRequest("onboarding@resend.dev", to, subject, html))
        .retrieve()
        .onStatus(HttpStatusCode::isError, response ->
            response.bodyToMono(String.class)
                    .flatMap(errorBody -> Mono.error(new RuntimeException("Resend API error: " + errorBody)))
        )
        .bodyToMono(String.class)
        .block(); // block here inside the service
}


    static class EmailRequest {
        private String from;
        private String to;
        private String subject;
        private String html;

        public EmailRequest(String from, String to, String subject, String html) {
            this.from = from;
            this.to = to;
            this.subject = subject;
            this.html = html;
        }

        // getters (optional but recommended for Jackson)
        public String getFrom() { return from; }
        public String getTo() { return to; }
        public String getSubject() { return subject; }
        public String getHtml() { return html; }
    }
}
