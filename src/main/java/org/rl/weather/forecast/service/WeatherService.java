package org.rl.weather.forecast.service;

import org.rl.weather.forecast.dao.CityDAO;
import org.rl.weather.forecast.dao.CityReferenceDAO;
import org.rl.weather.forecast.dto.DailyWeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for business logic.
 */
@Service
public class WeatherService {

	@Autowired
	private WeatherForecastClient weatherApiClient;

	@Autowired
	private CityDAO cityDAO;

	@Autowired
	private CityReferenceDAO referenceDAO;

	/**
	 * Gets the effective forecast based on a city name. Checks for a valid city name.
	 *
	 * @return Response with weather forecast plus additional data.
	 */
	public DailyWeatherResponse getForecast(String cityName) {
		int cityId = referenceDAO.getCityId(cityName);
		return weatherApiClient.forecastById(cityId);
	}

	/**
	 * Lists all registered cities.
	 */
	public List<String> listCities() {
		return cityDAO.listCities();
	}

	/**
	 * Effectively register a given city name to the database.
	 *
	 * @return The new list of registered cities.
	 */
	public List<String> registerCity(String cityName) {
		cityDAO.registerCity(cityName);
		return listCities();
	}
}
