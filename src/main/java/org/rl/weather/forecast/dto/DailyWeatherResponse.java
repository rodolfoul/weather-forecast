package org.rl.weather.forecast.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyWeatherResponse {
	private City city;
	private List<DayForecast> list;
}
