package org.rl.weather.forecast.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DayForecast {
	private Temperature temp;
	private String pressure;
	private String humidity;
	private String speed;
	private String clouds;
	private List<Weather> weather;
}
