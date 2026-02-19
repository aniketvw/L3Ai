package com.rcai.risk;

import com.rcai.shared.contracts.IncidentEvent;
import java.time.Instant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class RiskServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RiskServiceApplication.class, args);
    }
}

@RestController
@RequestMapping("/incident")
class IncidentController {

    @GetMapping
    Mono<IncidentEvent> simulate() {
        return Mono.just(new IncidentEvent("risk", "INC-001", "OK", Instant.now()));
    }
}
