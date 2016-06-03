import org.junit.Assert;
import org.junit.Test;
import org.rl.weather.forecast.dto.DailyWeatherResponse;
import org.rl.weather.forecast.service.WeatherForecastClient;

public class ForecastTest {

    @Test
    public void testIdForecast() {
        WeatherForecastClient wa = new WeatherForecastClient();
        DailyWeatherResponse resp = wa.forecastById(524901);
//        System.out.println(resp.getList().get(0).getDt().atZone(ZoneId.of("Europe/Moscow")));
        Assert.assertNotNull(resp);
    }
}