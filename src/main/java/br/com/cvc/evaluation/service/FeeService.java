package br.com.cvc.evaluation.service;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FeeService {
	// o ideal é que a comissão seja parametrizada via banco de dados
	// ou disponibilizada por um serviço externo
	private final BigDecimal fee;

	public FeeService(@Value("${booking.fee}") final BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal calculateFee(final BigDecimal paxPrice, final Long days) {
		log.info("Calculating fee: pax price {} for {} days", paxPrice, days);
		final var totalPricePax = paxPrice.multiply(BigDecimal.valueOf(days));
		final var fee = totalPricePax.multiply(this.fee);

		log.info("Fee calculated: {}", fee);
		return fee;
	}
}
