package org.rl.weather.forecast.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Failed accessing city data.")
public class CityAccessException extends RuntimeException {
	public CityAccessException(Exception e) {
		super(e);
	}
}
