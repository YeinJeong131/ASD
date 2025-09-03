package org.example.asd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"org.example.asd",
		"betterpedia.user"
})
public class AsdApplication {
	// http://localhost:8080
	public static void main(String[] args) {
		SpringApplication.run(AsdApplication.class, args);
	}
}
