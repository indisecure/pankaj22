package pankaj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SelfPingService {

    private static final Logger logger = LoggerFactory.getLogger(SelfPingService.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${self-ping.url}")
    private String selfPingUrl;

    // This method will run every 14 minutes.
    // 14 * 60 * 1000 = 840000 milliseconds
    @Scheduled(fixedRate = 840000)
    public void pingSelf() {
        try {
            logger.info("Pinging self at URL: {}", selfPingUrl);
            restTemplate.getForEntity(selfPingUrl, String.class);
            logger.info("Ping successful.");
        } catch (Exception e) {
            logger.error("Ping failed: {}", e.getMessage());
        }
    }
}