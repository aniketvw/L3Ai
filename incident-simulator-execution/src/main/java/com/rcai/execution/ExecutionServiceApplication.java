package com.rcai.execution;

import com.rcai.shared.contracts.IncidentEvent;
import java.time.Instant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ExecutionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExecutionServiceApplication.class, args);
    }
}

@RestController
@RequestMapping("/incident")
class IncidentController {

    @GetMapping
    Mono<IncidentEvent> simulate() {
        return Mono.just(new IncidentEvent("execution", "INC-001", "OK", Instant.now()));
    }
}
