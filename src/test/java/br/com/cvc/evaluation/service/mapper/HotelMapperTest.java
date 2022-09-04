package br.com.cvc.evaluation.service.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.cvc.evaluation.broker.dto.BrokerHotel;
import br.com.cvc.evaluation.fixtures.BrokerHotelFixture;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class HotelMapperTest {
    final private HotelMapper mapper = Mappers.getMapper(HotelMapper.class);

    @BeforeAll
    public static void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.cvc.evaluation.fixtures");
    }

    @Test
    void testToDomain() {
        final BrokerHotel brokerHotel = Fixture.from(BrokerHotel.class).gimme(BrokerHotelFixture.VALID);

        final var hotel = this.mapper.toDomain(brokerHotel);

        assertAll("domain",
                        () -> assertThat(hotel.getId(), is(brokerHotel.getId())),
                        () -> assertThat(hotel.getCityName(), is(brokerHotel.getCityName())),
                        () -> assertFalse(hotel.getRooms().isEmpty())
        );
    }

    @Test
    void testToDomainWithoutRoom() {
        final BrokerHotel brokerHotel = Fixture.from(BrokerHotel.class)
                        .gimme(BrokerHotelFixture.VALID_WITHOUT_ROOM);

        final var hotel = this.mapper.toDomain(brokerHotel);

        assertAll("domain",
                        () -> assertThat(hotel.getId(), is(brokerHotel.getId())),
                        () -> assertThat(hotel.getCityName(), is(brokerHotel.getCityName())),
                        () -> assertNull(hotel.getRooms())
        );
    }

    @Test
    void testToDomainWithEmptyRoomList() {
        final BrokerHotel brokerHotel = Fixture.from(BrokerHotel.class)
                        .gimme(BrokerHotelFixture.VALID_WITH_EMPTY_ROOM_LIST);

        final var hotel = this.mapper.toDomain(brokerHotel);

        assertAll("domain",
                        () -> assertThat(hotel.getId(), is(brokerHotel.getId())),
                        () -> assertThat(hotel.getCityName(), is(brokerHotel.getCityName())),
                        () -> assertTrue(hotel.getRooms().isEmpty())
        );
    }

    @Test
    void testNullToDomain() {
        final var hotel = this.mapper.toDomain(null);

        assertNull(hotel);
    }
}

