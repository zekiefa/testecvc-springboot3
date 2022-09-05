package br.com.cvc.evaluation.service.mapper;

import static br.com.cvc.evaluation.fixtures.FixtureUtil.nextBrokerHotelRoom;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import br.com.cvc.evaluation.domain.PriceDetail;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class RoomMapperTest {
    private final RoomMapper mapper = Mappers.getMapper(RoomMapper.class);

    @Test
    void testToDomain() {
        final var brokerHotelRoom = nextBrokerHotelRoom();
        final var room = this.mapper.toDomain(brokerHotelRoom);

        assertAll("domain",
                        () -> assertThat(room.getRoomID(), is(brokerHotelRoom.roomID())),
                        () -> assertThat(room.getCategoryName(), is(brokerHotelRoom.categoryName())),
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

