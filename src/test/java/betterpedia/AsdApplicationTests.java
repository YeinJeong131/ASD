package betterpedia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.springframework.boot.test.context.SpringBootTest;

@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
@SpringBootTest
class AsdApplicationTests {

	@Test
	void contextLoads() {
	}

}
