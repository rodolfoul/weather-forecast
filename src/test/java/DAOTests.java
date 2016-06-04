import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rl.weather.forecast.dao.CityDAO;
import org.rl.weather.forecast.dao.CityReferenceDAO;
import org.rl.weather.forecast.exception.CityAlreadyRegisteredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TestBeansConfig.class)
public class DAOTests {

	@Autowired
	private CityDAO cityDAO;

	@Autowired
	private CityReferenceDAO cityReferenceDAO;

	@Autowired
	private Connection con;

	@Test(expected = CityAlreadyRegisteredException.class)
	public void testRegisterRepeated() {
		cityDAO.registerCity("Blumenau");
	}

	@Test
	public void testListAndRegisterCity() {
		cityDAO.registerCity("Curitiba");
		Assert.assertTrue(cityDAO.listCities().contains("Curitiba"));
	}

	@Test
	public void testDeleteCity() {
		try {
			cityDAO.registerCity("Blumenau");
		} catch (CityAlreadyRegisteredException e) {
		}
		Assert.assertTrue(cityDAO.listCities().contains("Blumenau"));
		cityDAO.deleteCity("Blumenau");
		Assert.assertFalse(cityDAO.listCities().contains("Blumenau"));
	}

	@Test
	public void testCityId() {
		int blumenau = cityReferenceDAO.getCityId("Blumenau");
		Assert.assertEquals(blumenau, 6323074);
	}

	@Test
	public void testInsertCityReference() throws SQLException {
		cityReferenceDAO.insertCityReference(123, "TestCity", "TE");

		try (Statement st = con.createStatement();
		     ResultSet rs = st.executeQuery("SELECT * FROM city_reference WHERE api_id = 123")) {
			Assert.assertTrue(rs.next());
			Assert.assertEquals(rs.getInt(1), 123);
			Assert.assertEquals(rs.getString(2), "TestCity");
			Assert.assertEquals(rs.getString(3), "TE");
		}
	}
}
