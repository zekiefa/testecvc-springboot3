package br.com.cvc.evaluation.service.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import br.com.cvc.evaluation.broker.dto.BrokerHotelRoom;
import br.com.cvc.evaluation.domain.PriceDetail;
import br.com.cvc.evaluation.fixtures.BrokerHotelRoomFixture;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class RoomMapperTest {
    private final RoomMapper mapper = Mappers.getMapper(RoomMapper.class);

    @BeforeAll
    public static void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.cvc.evaluation.fixtures");
    }

    @Test
    void testToDomain() {
        final BrokerHotelRoom brokerHotelRoom = Fixture.from(BrokerHotelRoom.class)
                        .gimme(BrokerHotelRoomFixture.VALIDO);

        final var room = this.mapper.toDomain(brokerHotelRoom);

        assertAll("domain",
                        () -> assertThat(room.getRoomID(), is(brokerHotelRoom.getRoomID())),
                        () -> assertThat(room.getCategoryName(), is(brokerHotelRoom.getCategoryName())),
                        () -> assertThat(room.getPriceDetail(), is(PriceDetail.builder()
                                        .pricePerDayAdult(BigDecimal.ZERO)
                                        .pricePerDayAdult(BigDecimal.ZERO).build())),
                        () -> assertThat(room.getTotalPrice(), is(BigDecimal.ZERO))
        );
    }

    @Test
    void testNullToDomain() {
        final var room = this.mapper.toDomain(null);

        assertNull(room);
    }
}

