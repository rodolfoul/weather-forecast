import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.rl.weather.forecast.dao.CityDAO;
import org.rl.weather.forecast.dto.DailyWeatherResponse;
import org.rl.weather.forecast.exception.CityAlreadyRegisteredException;
import org.rl.weather.forecast.exception.InvalidCityNameException;
import org.rl.weather.forecast.service.WeatherForecastApiClient;
import org.rl.weather.forecast.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TestBeansConfig.class)
@WebAppConfiguration
public class WeatherServiceTest {

	public static final String MAIN_CITY = "Curitiba";

	@InjectMocks
	@Autowired
	private WeatherService weatherService;

	@Mock
	private WeatherForecastApiClient client;

	@Autowired
	private CityDAO cityDAO;


	private DailyWeatherResponse standardResponse = new DailyWeatherResponse();

	@Before
	public void setupMocks() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(client.forecastById(6322752)).thenReturn(standardResponse); //Code for Curitiba
	}

	@Test
	public void testWeatherForecast() {
		DailyWeatherResponse resp = weatherService.getForecast(MAIN_CITY);
		Assert.assertTrue(resp == standardResponse);
	}

	@Test
	public void testCityListing() {
		try {
			weatherService.registerCity(MAIN_CITY);
		} catch (CityAlreadyRegisteredException e) {
		}

		List<String> cities = weatherService.listCities();
		Assert.assertFalse(cities.isEmpty());
		Assert.assertTrue(cities.contains(MAIN_CITY));
	}

	@Test(expected = InvalidCityNameException.class)
	public void testFalseCityName() {
		weatherService.registerCity("asdf");
	}

	@Test
	public void testCityRegister() {
		cityDAO.deleteCity(MAIN_CITY);
		Assert.assertFalse(weatherService.listCities().contains(MAIN_CITY));
		List<String> cities = weatherService.registerCity(MAIN_CITY);
		Assert.assertTrue(cities.contains(MAIN_CITY));
	}
}