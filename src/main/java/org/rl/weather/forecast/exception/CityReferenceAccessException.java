package org.rl.weather.forecast.exception;


import java.sql.SQLException;

public class CityReferenceAccessException extends RuntimeException {
	public CityReferenceAccessException(SQLException e) {
		super(e);
	}

	public String getErrorCode() {
		return ((SQLException) getCause()).getSQLState();
	}
}
