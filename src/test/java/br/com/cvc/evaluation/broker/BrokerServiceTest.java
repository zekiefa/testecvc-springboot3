package br.com.cvc.evaluation.broker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.cvc.evaluation.broker.dto.BrokerHotel;
import br.com.cvc.evaluation.config.WebClientConfig;
import br.com.cvc.evaluation.config.WireMockConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@SpringBootTest(classes = {WireMockConfig.class, WebClientConfig.class})
@WithMockUser
class BrokerServiceTest {
    @Autowired
    private BrokerService brokerService;

    @Test
    void testFindHotelsByCity() {
        final var cityCode = 55;

        final var hotelsByCity = this.brokerService.findHotelsByCity(cityCode);

        assertAll("success",
                        () -> assertThat(hotelsByCity, not(emptyCollectionOf(BrokerHotel.class))),
                        () -> assertThat(hotelsByCity.size(), is(2)),
                        () -> assertTrue(hotelsByCity.stream()
                                        .allMatch(brokerHotel -> brokerHotel.getCityCode().equals(cityCode)))
        );
    }

    void testFindNoHotelsByCity() {
        final var cityCode = 56;

        final var hotelsByCity = this.brokerService.findHotelsByCity(cityCode);

        assertThat(hotelsByCity, emptyCollectionOf(BrokerHotel.class));
    }

    @Test
    void testGetHotelDetails() {
        final var hotelCode = 1;

        final var hotelDetails = this.brokerService.getHotelDetails(hotelCode);

        assertAll("success",
                        () -> assertTrue(hotelDetails.isPresent()),
                        () -> assertThat(hotelDetails.get().getId(), is(hotelCode))
        );
    }

    @Test
    void testGetNoHotelDetails() {
        final var hotelCode = 99;

        assertThrowsExactly(WebClientResponseException.NotFound.class,
                        () -> this.brokerService.getHotelDetails(hotelCode));
    }
}
