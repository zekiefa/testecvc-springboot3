package br.com.cvc.evaluation.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FeeService {
	private static final Logger log = LoggerFactory.getLogger(FeeService.class);
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
