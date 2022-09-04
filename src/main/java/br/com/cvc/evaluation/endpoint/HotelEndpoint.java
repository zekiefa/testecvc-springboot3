package br.com.cvc.evaluation.endpoint;

import br.com.cvc.evaluation.domain.Hotel;
import br.com.cvc.evaluation.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels")
public class HotelEndpoint {
	@Autowired
	private BookingService bookingService;

	@GetMapping("/{id}")
	public ResponseEntity<Hotel> find(@PathVariable("id") final Integer id) {

		return ResponseEntity.of(this.bookingService.getHotelDetails(id));
	}

}
