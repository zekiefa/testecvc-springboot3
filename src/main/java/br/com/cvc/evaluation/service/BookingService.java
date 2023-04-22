package br.com.cvc.evaluation.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import br.com.cvc.evaluation.broker.BrokerService;
import br.com.cvc.evaluation.broker.dto.BrokerHotel;
import br.com.cvc.evaluation.broker.dto.BrokerHotelRoom;
import br.com.cvc.evaluation.domain.Hotel;
import br.com.cvc.evaluation.domain.PriceDetail;
import br.com.cvc.evaluation.domain.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
	private static final Logger log = LoggerFactory.getLogger(BookingService.class);
	private static final Long ONE_DAY = Long.valueOf("1");
	private static final Integer ONE_PAX = 1;

	private final BrokerService brokerService;
	private final FeeService feeService;

	public BookingService(final BrokerService brokerService,
					final FeeService feeService) {
		this.brokerService = brokerService;
		this.feeService = feeService;
	}

	private BigDecimal calculateTotalPrice(final BigDecimal paxPrice, final Long days) {
		log.info("Calculating total price: pax price {} for {} days", paxPrice, days);
		final var fee = this.feeService.calculateFee(paxPrice, days);

		return paxPrice.add(fee).multiply(BigDecimal.valueOf(days));
	}

	private Long calculatePeriod(final LocalDate checkin, final LocalDate checkout) {
		log.info("Calculating period: checking {}, checkout {}", checkin, checkout);
		return checkin.until(checkout, ChronoUnit.DAYS);
	}

	private Room calculateTotalPrice(final BrokerHotelRoom brokerHotelRoom, final Long days, final Integer adults,
					final Integer child) {
		log.info("Calculating total price: room {}, {} days, {} adults, {} child",
						brokerHotelRoom.categoryName(), days, adults, child);
		var pricePerDayAdult = BigDecimal.ZERO;
		var pricePerDayChild = BigDecimal.ZERO;
		var totalPrice = BigDecimal.ZERO;

		if (adults > 0) {
			pricePerDayAdult = this.calculateTotalPrice(brokerHotelRoom.price().adult(), ONE_DAY);
			totalPrice = totalPrice.add(brokerHotelRoom.price().adult().multiply(BigDecimal.valueOf(days)));
		}

		if (child > 0) {
			pricePerDayChild = this.calculateTotalPrice(brokerHotelRoom.price().child(), ONE_DAY);
			totalPrice = totalPrice.add(brokerHotelRoom.price().child().multiply(BigDecimal.valueOf(days)));
		}

		log.info("Total price: {}", totalPrice);

		return new Room(brokerHotelRoom.roomID(),
						brokerHotelRoom.categoryName(),
						totalPrice,
						new PriceDetail(pricePerDayAdult, pricePerDayChild));
	}

	private Hotel calculateBooking(final BrokerHotel brokerHotel, final Long days, final Integer adults,
					final Integer child) {
		log.info("Calculating booking: hotel {}, {} days, {} adults, {} child",
						brokerHotel.name(), days, adults, child);
		final var rooms = brokerHotel.rooms().stream()
						.map(brokerHotelRoom -> this.calculateTotalPrice(brokerHotelRoom, days, adults, child))
						.toList();

		return new Hotel(brokerHotel.id(), brokerHotel.cityName(), brokerHotel.name(), rooms);
	}

	public Optional<Hotel> getHotelDetails(final Integer codeHotel) {
		log.info("Finding hotel details: {}", codeHotel);
		final var hotelDetails = this.brokerService.getHotelDetails(codeHotel);

		return hotelDetails.map(brokerHotel -> this.calculateBooking(brokerHotel, ONE_DAY, ONE_PAX, ONE_PAX));
	}

	public List<Hotel> findHotels(final Integer cityCode, final LocalDate checkin, final LocalDate checkout,
					final Integer adults, final Integer child) {
		log.info("Finding hotels: city {}, checkin {}, checkout {}", cityCode, checkin, checkout);
		final var hotels = this.brokerService.findHotelsByCity(cityCode);
		final var period = this.calculatePeriod(checkin, checkout);

		log.info("Result of searching hotels: {}", hotels.size());
		return hotels.stream().map(brokerHotel -> this.calculateBooking(brokerHotel, period, adults, child))
						.toList();
	}
}
