package org.rl.weather.forecast.db.preparation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.tools.RunScript;
import org.rl.weather.forecast.dao.CityReferenceDAO;
import org.rl.weather.forecast.exception.CityReferenceAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.zip.GZIPInputStream;

/**
 * Prepares the database for proper use, i.e. creates tables and fills cities that should serve as reference.
 */
@Component
public class DbPreparer {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Connection con;

	@Autowired
	private CityReferenceDAO cityReferenceDAO;

	@PostConstruct
	public void init() {
		createTables();
		buildValidationTable();
	}

	/**
	 * Creates all the tables based on create-db.sql
	 */
	public void createTables() {
		try (Reader r = new InputStreamReader(getClass().getResourceAsStream("/db/create-db.sql"), StandardCharsets.UTF_8)) {
			RunScript.execute(con, r);
		} catch (IOException | SQLException e) {
			logger.error("Error creating default tables, exiting...", e);
			System.exit(1);
		}
	}

	/**
	 * Builds the city reference table by importing the json file given by open weather map.
	 *
	 * @see <a href="http://openweathermap.org/forecast16">Weaher api</a> for the instructions:
	 * List of city ID city.list.json.gz can be downloaded here
	 * <a href="http://bulk.openweathermap.org/sample/">http://bulk.openweathermap.org/sample/</a>
	 */
	private void buildValidationTable() {
		if (cityReferenceDAO.isCityReferenceFilled()) {
			return;
		}

		try {
			logger.info("Going to fill reference cities for the first application start, please wait a moment...");
			URL url = new URL("http://bulk.openweathermap.org/sample/city.list.json.gz");
			URLConnection urlConnection = url.openConnection();


			try (InputStream is = urlConnection.getInputStream();
			     GZIPInputStream gzStream = new GZIPInputStream(is);
			     InputStreamReader reader = new InputStreamReader(gzStream, StandardCharsets.UTF_8);
			     BufferedReader br = new BufferedReader(reader)) {

				ObjectMapper om = new ObjectMapper();
				String line;
				while ((line = br.readLine()) != null) {
					JsonNode v = om.readValue(line, JsonNode.class);
					logger.debug("Adding city '{}'", v.get("name").textValue());
					try {
						cityReferenceDAO.insertCityReference(v.get("_id").asInt(), v.get("name").textValue(), v.get("country").textValue());
					} catch (CityReferenceAccessException e) {
						if ("23505".equals(e.getErrorCode())) { //ignore non unique city names as we cannot register cities with repeated names
							continue;
						}
						throw e;
					}
				}
			}
		} catch (IOException | CityReferenceAccessException e) {
			logger.error("Error adding city references to database", e);
			System.exit(1);
		}
		logger.info("Finished filling reference cities.");
	}
}
