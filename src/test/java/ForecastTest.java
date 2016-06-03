import org.junit.Assert;
import org.junit.Test;
import org.rl.weather.forecast.dto.DailyWeatherResponse;
import org.rl.weather.forecast.service.WeatherForecastClient;

public class ForecastTest {

	@Test
	public void testIdForecast() {
		WeatherForecastClient wa = new WeatherForecastClient();
		DailyWeatherResponse resp = wa.forecastById(524901);
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