package org.rl.weather.forecast.dao;

import org.rl.weather.forecast.exception.CityAccessException;
import org.rl.weather.forecast.exception.CityAlreadyRegisteredException;
import org.rl.weather.forecast.exception.InvalidCityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides access to all registered cities.
 */
@Repository
public class CityDAO {

	@Autowired
	private Connection con;

	//TODO Mudar arquitetura para selecionar case insensitive
	public void registerCity(String cityName) {
		try (PreparedStatement ps = con.prepareStatement("INSERT INTO city VALUES (?)")) {

			ps.setString(1, cityName);
			ps.execute();
		} catch (SQLException e) {
			if ("23506".equals(e.getSQLState())) {
				throw new InvalidCityException(cityName);
			} else if ("23505".equals(e.getSQLState())) {
				throw new CityAlreadyRegisteredException(cityName);
			}
		}
	}

	public List<String> listCities() {
		List<String> cities = new ArrayList<>();

		try (Statement st = con.createStatement();
		     ResultSet rs = st.executeQuery("SELECT name FROM city")) {

			while (rs.next()) {
				cities.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			throw new CityAccessException(e);
		}
		return cities;
	}
}
