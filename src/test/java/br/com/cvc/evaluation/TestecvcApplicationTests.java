package br.com.cvc.evaluation;

import br.com.cvc.evaluation.config.MockServerConfig;
import br.com.cvc.evaluation.config.WebClientTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {
				WebClientTestConfig.class,
				MockServerConfig.class})
public class TestecvcApplicationTests {

	@Test
	public void contextLoads() {
	}

}
