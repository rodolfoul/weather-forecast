package org.rl.weather.forecast.dao;

import org.rl.weather.forecast.exception.CityAlreadyRegisteredException;
import org.rl.weather.forecast.exception.InvalidCityNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CityDAO {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Connection con;

	//TODO dar um jeito nas sqlexceptions
	public void registerCity(String cityName) {
		try (PreparedStatement ps = con.prepareStatement("INSERT INTO city VALUES (?)")) {

			ps.setString(1, cityName);
			ps.execute();
		} catch (SQLException e) {
			if ("23506".equals(e.getSQLState())) {
				throw new InvalidCityNameException(cityName);
			} else if ("23505".equals(e.getSQLState())) {
				throw new CityAlreadyRegisteredException(cityName);
			}
		}
	}

	public List<String> listCities() throws SQLException {
		List<String> cities = new ArrayList<>();

		try (Statement st = con.createStatement();
		     ResultSet rs = st.executeQuery("SELECT name FROM city")) {

			while (rs.next()) {
				cities.add(rs.getString("name"));
			}
		}
		return cities;
	}

	public String getCityId(String name) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement("SELECT api_id FROM city_reference WHERE name = ?")) {
			ps.setString(1, name);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					return rs.getString("api_id");
				}
			}
		}
		return "";
	}
}
