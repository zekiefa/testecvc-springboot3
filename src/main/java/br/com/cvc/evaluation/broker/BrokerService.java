package br.com.cvc.evaluation.broker;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import br.com.cvc.evaluation.broker.dto.BrokerHotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BrokerService {
	private final WebClient webClient;

	@Autowired
	public BrokerService(final WebClient brokerServiceClient) {
		this.webClient = brokerServiceClient;
	}

	public List<BrokerHotel> findHotelsByCity(final Integer codeCity) {
		final ResponseEntity<List<BrokerHotel>> response = this.webClient
						.get()
						.uri(String.format("/hotels/avail/%d", codeCity))
						.retrieve()
						.toEntityList(BrokerHotel.class)
						.block();

		assert response != null;
		return response.getBody();
	}

	public Optional<BrokerHotel> getHotelDetails(final Integer codeHotel) {
		final ResponseEntity<List<BrokerHotel>> response = this.webClient
						.get()
						.uri(String.format("/hotels/%d", codeHotel))
						.retrieve()
						.toEntityList(BrokerHotel.class)
						.block();

		assert response != null;
		if (!Objects.requireNonNull(response.getBody()).isEmpty()) {
			return Optional.of(response.getBody().get(0));
		}

		return Optional.empty();
	}
}
