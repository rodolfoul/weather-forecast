package org.rl.weather.forecast.dao;

import org.rl.weather.forecast.exception.CityAccessException;
import org.rl.weather.forecast.exception.CityReferenceAccessException;
import org.rl.weather.forecast.exception.InvalidCityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@Repository
public class CityReferenceDAO {
	@Autowired
	private Connection con;

	public void insertCityReference(int id, String name, String countryCode) {
		try (PreparedStatement ps = con.prepareStatement("INSERT INTO city_reference (api_id, name, country_code) VALUES (?, ?, ?)")) {
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setString(3, countryCode);

			ps.execute();
		} catch (SQLException e) {
			throw new CityReferenceAccessException(e);
		}
	}

	public boolean isCityReferenceFilled() {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT COUNT(api_id) FROM city_reference");

			while (rs.next()) {
				int count = rs.getInt(1);
				if (count > 162000) {
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			throw new CityReferenceAccessException(e);
		}
	}

	public int getCityId(String name) {
		try (PreparedStatement ps = con.prepareStatement("SELECT api_id FROM city_reference WHERE name = ?")) {
			ps.setString(1, name);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					return rs.getInt("api_id");
				}
			}
		} catch (SQLException e) {
			throw new CityAccessException(e);
		}
		throw new InvalidCityException(name);
	}
}
