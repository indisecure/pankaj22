package pankaj.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ResendService {

    private final WebClient webClient;

    public ResendService(@Value("${resend.api.key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.resend.com")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public Mono<String> sendEmail(String to, String subject, String html) {
        return webClient.post()
                .uri("/emails")
                .bodyValue(new EmailRequest("onboarding@resend.dev", to, subject, html))
                .retrieve()
                .bodyToMono(String.class);
    }

    static class EmailRequest {
        public String from;
        public String to;
        public String subject;
        public String html;

        public EmailRequest(String from, String to, String subject, String html) {
            this.from = from;
            this.to = to;
            this.subject = subject;
            this.html = html;
        }
    }
}


