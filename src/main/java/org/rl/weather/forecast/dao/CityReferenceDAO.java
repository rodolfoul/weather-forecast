package org.rl.weather.forecast.dao;

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

	public void insertCityReference(int id, String name, String countryCode) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement("INSERT INTO city_reference (api_id, name, country_code) VALUES (?, ?, ?)")) {
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setString(3, countryCode);

			ps.execute();
		}
	}

	public boolean isCityReferenceFilled() throws SQLException {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT COUNT(api_id) FROM city_reference");
		while (rs.next()) {
			int count = rs.getInt(1);
			if (count > 162000) {
				return true;
			}
		}
		return false;
	}
}
