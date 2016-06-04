package org.rl.weather.forecast.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@AllArgsConstructor
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid city name")
public class InvalidCityNameException extends RuntimeException {
	private String searchedName;
}
