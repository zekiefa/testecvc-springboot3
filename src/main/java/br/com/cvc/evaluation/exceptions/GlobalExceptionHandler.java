package br.com.cvc.evaluation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookingInvalidPeriodException.class)
    ProblemDetail handleBookingInvalidPeriodException(BookingInvalidPeriodException e) {
        final var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Invalid Period");

        return problemDetail;
    }

    @ExceptionHandler(HotelNotFoundException.class)
    ProblemDetail handleHotelNotFoundException(HotelNotFoundException e) {
        final var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Hotel Not Found");

        return problemDetail;
    }
}
