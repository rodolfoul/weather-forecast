package org.rl.weather.forecast.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@AllArgsConstructor
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "City already registered")
public class CityAlreadyRegisteredException extends RuntimeException {
	private String cityName;
}
