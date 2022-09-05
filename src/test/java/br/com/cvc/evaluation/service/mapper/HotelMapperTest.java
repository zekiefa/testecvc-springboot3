package br.com.cvc.evaluation.service.mapper;

import static br.com.cvc.evaluation.fixtures.FixtureUtil.nextBrokerHotel;
import static br.com.cvc.evaluation.fixtures.FixtureUtil.nextBrokerHotelWithEmptyRooms;
import static br.com.cvc.evaluation.fixtures.FixtureUtil.nextBrokerHotelWithoutRooms;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class HotelMapperTest {
    final private HotelMapper mapper = Mappers.getMapper(HotelMapper.class);
    private

    @Test
    void testToDomain() {
        final var brokerHotel = nextBrokerHotel();
        final var hotel = this.mapper.toDomain(brokerHotel);

        assertAll("domain",
                        () -> assertThat(hotel.getId(), is(brokerHotel.id())),
                        () -> assertThat(hotel.getCityName(), is(brokerHotel.cityName())),
                        () -> assertFalse(hotel.getRooms().isEmpty())
        );
    }

    @Test
    void testToDomainWithoutRoom() {
        final var brokerHotel = nextBrokerHotelWithoutRooms();
        final var hotel = this.mapper.toDomain(brokerHotel);

        assertAll("domain",
                        () -> assertThat(hotel.getId(), is(brokerHotel.id())),
                        () -> assertThat(hotel.getCityName(), is(brokerHotel.cityName())),
                        () -> assertNull(hotel.getRooms())
        );
    }

    @Test
    void testToDomainWithEmptyRoomList() {
        final var brokerHotel = nextBrokerHotelWithEmptyRooms();
        final var hotel = this.mapper.toDomain(brokerHotel);

        assertAll("domain",
                        () -> assertThat(hotel.getId(), is(brokerHotel.id())),
                        () -> assertThat(hotel.getCityName(), is(brokerHotel.cityName())),
                        () -> assertTrue(hotel.getRooms().isEmpty())
        );
    }

    @Test
    void testNullToDomain() {
        final var hotel = this.mapper.toDomain(null);

        assertNull(hotel);
    }
}

