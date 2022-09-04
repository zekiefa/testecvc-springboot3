package br.com.cvc.evaluation;

import br.com.cvc.evaluation.config.WebClientConfig;
import br.com.cvc.evaluation.config.WireMockConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {
				WebClientConfig.class,
				WireMockConfig.class})
public class TestecvcApplicationTests {

	@Test
	public void contextLoads() {
	}

}
