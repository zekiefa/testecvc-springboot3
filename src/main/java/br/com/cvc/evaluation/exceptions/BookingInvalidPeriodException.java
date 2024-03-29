package br.com.cvc.evaluation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookingInvalidPeriodException extends RuntimeException {

    public BookingInvalidPeriodException(final String message) {
        super(message);
    }
}
