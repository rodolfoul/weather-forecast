import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rl.weather.forecast.WebConfig;
import org.rl.weather.forecast.dto.DailyWeatherResponse;
import org.rl.weather.forecast.service.WeatherForecastApiClient;
import org.rl.weather.forecast.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(WebConfig.class)
@WebAppConfiguration
public class ForecastTest {

	@Autowired
	private WeatherService weatherService;

	@Autowired
	private WeatherForecastApiClient client;

	@Test
	public void testIdForecast() {
		//TODO mock rest client
		DailyWeatherResponse resp = client.forecastById(524901);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getList());
		Assert.assertFalse(resp.getList().isEmpty());
	}

	@Test
	public void testWeatherService() {

	}

	@Test
	public void testCityDAO() {

	}
}