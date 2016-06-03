package org.rl.weather.forecast.controller;

import org.rl.weather.forecast.dao.CityDAO;
import org.rl.weather.forecast.dto.DailyWeatherResponse;
import org.rl.weather.forecast.service.WeatherForecastClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class WeatherRestController {

	@Autowired
	private WeatherForecastClient weatherApiClient;

	@Autowired
	private CityDAO cityDAO;

	@ResponseBody
	@RequestMapping(value = "/previsão/{cityName}", method = RequestMethod.GET)
	public DailyWeatherResponse getForecast(@PathVariable String cityName) throws SQLException {
		String cityId = cityDAO.getCityId(cityName);
		return weatherApiClient.forecastById(cityId);//TODO tratar cityId == null
	}

	@ResponseBody
	@RequestMapping(value = "/cidades-cadastradas", method = RequestMethod.GET)
	public List<String> getCities() throws SQLException {
		return cityDAO.listCities();
	}

	@ResponseBody
	@RequestMapping(value = "/cadastrar-cidade", method = RequestMethod.POST)
	public List<String> registerCity(@RequestBody String cityName) throws SQLException { //TODO Remover sqlexceptions
		cityDAO.registerCity(cityName); //TODO testar cityName == null
		//TODO tratar erros
		return getCities();
	}
}