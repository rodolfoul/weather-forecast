package org.rl.weather.forecast.service;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.rl.weather.forecast.dto.DailyWeatherResponse;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Serves the purpose of accessing the weather forecast service
 */
@Service
public class WeatherForecastApiClient {
	private final String apiKey = "eb8b1a9405e659b2ffc78f0a520b1a46";

	private final Client apiClient;
	private final WebTarget mainTarget;
	private final WebTarget dailyForecastById;


	public WeatherForecastApiClient() {
		apiClient = ClientBuilder.newClient().register(JacksonFeature.class);
		mainTarget = apiClient.target("http://api.openweathermap.org/data/2.5/forecast").queryParam("appid", apiKey).queryParam("mode", "json");
		dailyForecastById = mainTarget.path("daily").queryParam("cnt", 5).queryParam("units", "metric");
	}

	/**
	 * Gets a 5 day forecast based on a city id.
	 *
	 * @param id the city id from which the forecast should be taken.
	 * @return The forecast response.
	 */
	public DailyWeatherResponse forecastById(int id) {
		WebTarget target = dailyForecastById.queryParam("id", id);
		return target.request(MediaType.APPLICATION_JSON_TYPE).get().readEntity(DailyWeatherResponse.class);
	}
}
