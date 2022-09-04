package br.com.cvc.evaluation.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.time.DayOfWeek;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FeeServiceTest {
    private static final BigDecimal FEE = BigDecimal.valueOf(0.7);
    FeeService feeService = new FeeService(FEE);

    @Test
    void testCalculateFee() {
        // Arranges
        final var paxPrice = BigDecimal.valueOf(259.67);
        final var days = Long.valueOf(DayOfWeek.values().length);

        // Act
        final var result = feeService.calculateFee(paxPrice, days);

        // Assert
        assertThat(paxPrice.multiply(BigDecimal.valueOf(days)).multiply(FEE), is(result));
    }
}
