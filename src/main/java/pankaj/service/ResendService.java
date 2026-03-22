package pankaj.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ResendService {
    private final String apiKey;
    private final WebClient webClient;

    public ResendService(@Value("${resend.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
                .baseUrl("https://api.resend.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String sendEmail(String to, String subject, String html) {
        return webClient.post()
                .uri("/emails")
                .bodyValue(new EmailRequest("onboarding@resend.dev", to, subject, html))
                .retrieve()
                .onStatus(status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Resend API error: " + errorBody))))
                .bodyToMono(String.class)
                .block(); // block inside service
    }

    static class EmailRequest {
        private final String from;
        private final String to;
        private final String subject;
        private final String html;

        public EmailRequest(String from, String to, String subject, String html) {
            this.from = from;
            this.to = to;
            this.subject = subject;
            this.html = html;
        }

        public String getFrom() { return from; }
        public String getTo() { return to; }
        public String getSubject() { return subject; }
        public String getHtml() { return html; }
    }
}
