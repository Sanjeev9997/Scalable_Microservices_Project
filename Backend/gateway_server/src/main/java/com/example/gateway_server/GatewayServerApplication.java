package com.example.gateway_server;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class GatewayServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}
	@PostConstruct
	public void init() {
		// Ensures OpenTelemetry trace state bridges into reactive streams smoothly
		Hooks.enableAutomaticContextPropagation();
	}
}
