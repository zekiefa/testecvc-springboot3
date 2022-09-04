package br.com.cvc.evaluation.endpoint;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cvc.evaluation.domain.Hotel;
import br.com.cvc.evaluation.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingEndpoint {
	@Autowired
	private BookingService bookingService;

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	@GetMapping("/{cityCode}/{checkin}/{checkout}/{adults}/{children}")
	@ResponseStatus(HttpStatus.OK)
	public List<Hotel> find(@PathVariable("cityCode") final Integer cityCode,
			@PathVariable("checkin") final String checkin, @PathVariable("checkout") final String checkout,
			@PathVariable("adults") final Integer adults, @PathVariable("children") final Integer children) {

		return this.bookingService.findHotels(cityCode, this.parseDate(checkin), this.parseDate(checkout), adults,
				children);
	}

	private LocalDate parseDate(final String date) {
		return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
	}
}
