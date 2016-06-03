package org.rl.weather.forecast.controller;

import org.rl.weather.forecast.dao.CityDAO;
import org.rl.weather.forecast.dao.CityReferenceDAO;
import org.rl.weather.forecast.dto.DailyWeatherResponse;
import org.rl.weather.forecast.service.WeatherForecastClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Simple controller layer that uses Spring MVC framework to provide the view with the model.
 */
@RestController
@RequestMapping("/rest")
public class WeatherRestController {

	@Autowired
	private WeatherForecastClient weatherApiClient;

	@Autowired
	private CityDAO cityDAO;

	@Autowired
	private CityReferenceDAO referenceDAO;

	/**
	 * Gets the effective forecast based on a city name. Checks for a valid city name.
	 * @return Response with weather forecast plus additional data.
	 */
	@ResponseBody
	@RequestMapping(value = "/previsão/{cityName}", method = RequestMethod.GET)
	public DailyWeatherResponse getForecast(@PathVariable String cityName) {
		int cityId = referenceDAO.getCityId(cityName);
		return weatherApiClient.forecastById(cityId);
	}

	/**
	 * Lists all registered cities.
	 */
	@ResponseBody
	@RequestMapping(value = "/cidades-cadastradas", method = RequestMethod.GET)
	public List<String> getCities() {
		return cityDAO.listCities();
	}

	/**
	 * Effectively register a given city name to the database.
	 * @return The new list of registered cities.
	 */
	@ResponseBody
	@RequestMapping(value = "/cadastrar-cidade", method = RequestMethod.POST)
	public List<String> registerCity(@RequestBody String cityName) {
		cityDAO.registerCity(cityName);
		return getCities();
	}
}