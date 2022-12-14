package br.com.cvc.evaluation.service;

import static br.com.cvc.evaluation.fixtures.FixtureUtil.nextBrokerHotel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.com.cvc.evaluation.broker.BrokerService;
import br.com.cvc.evaluation.broker.dto.BrokerHotel;
import br.com.cvc.evaluation.domain.Hotel;
import br.com.cvc.evaluation.service.mapper.HotelMapper;
import br.com.cvc.evaluation.service.mapper.RoomMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @Mock
    BrokerService brokerService;

    @Mock
    FeeService feeService;

    @Spy
    final HotelMapper hotelMapper = Mappers.getMapper(HotelMapper.class);

    @Spy
    final RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

    @InjectMocks
    BookingService bookingService;

    @Test
    void testGetHotelDetails() {
        // Arranges
        final var brokerHotel = nextBrokerHotel();
        final var fee = BigDecimal.ONE;
        when(brokerService.getHotelDetails(anyInt())).thenReturn(Optional.of(brokerHotel));
        when(feeService.calculateFee(any(), anyLong())).thenReturn(fee);

        // Act
        final var hotel = bookingService.getHotelDetails(1).orElse(Hotel.builder().build());

        // Asserts
        assertAll("hotel-details",
                        () -> assertThat(hotel.getId(), is(brokerHotel.id())),
                        () -> assertThat(hotel.getCityName(), is(brokerHotel.cityName())),
                        () -> assertFalse(hotel.getRooms().isEmpty()),
                        () -> verify(brokerService).getHotelDetails(anyInt()),
                        () -> verify(feeService, times(brokerHotel.rooms().size() * 2))
                                        .calculateFee(any(), anyLong())
        );
    }

    @Test
    void testFindHotels() {
        // Arranges
        final var brokerHotels = List.of(nextBrokerHotel(), nextBrokerHotel());
        final var fee = BigDecimal.ONE;
        when(brokerService.findHotelsByCity(anyInt())).thenReturn(brokerHotels);
        when(feeService.calculateFee(any(), anyLong())).thenReturn(fee);

        // Act
        final var checkin = LocalDate.now();
        final var checkout = checkin.plusDays(DayOfWeek.values().length);
        final var hotels = bookingService.findHotels(27, checkin, checkout, 3, 2);

        // Asserts
        assertAll("find-hotels",
                        () -> assertFalse(hotels.isEmpty()),
                        () -> assertThat(hotels.size(), is(2)),
                        () -> verify(brokerService).findHotelsByCity(anyInt()),
                        () -> verify(feeService, times(brokerHotels.size() *
                                        brokerHotels.stream().map(BrokerHotel::rooms).mapToInt(List::size).sum()))
                                        .calculateFee(any(), anyLong())
        );
    }
}