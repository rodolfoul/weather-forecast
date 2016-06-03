package org.rl.weather.forecast.db.preparation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.tools.RunScript;
import org.rl.weather.forecast.dao.CityReferenceDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.zip.GZIPInputStream;

@Component
public class DbPreparer {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Connection con;

	@Autowired
	private CityReferenceDAO cityReferenceDAO;

	@PostConstruct
	public void init() throws IOException, SQLException {
		createTables();
		buildValidationTable();
	}

	private void buildValidationTable() throws SQLException {
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
					} catch (SQLException e) {
						if ("23505".equals(e.getSQLState())) { //ignore non unique city names as we cannot register cities with repeated names
							continue;
						}
						throw e;
					}
				}
			}
		} catch (IOException | SQLException e) {
			logger.error("Should not reach this point!", e); //TODO Check log comment
			System.exit(1);
		}
		logger.info("Finished filling reference cities.");
	}

	public void createTables() throws IOException, SQLException {
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(getClass().getResource("/db/create-db.sql").toURI()), StandardCharsets.UTF_8)) {
			RunScript.execute(con, reader);
		} catch (URISyntaxException e) {
			logger.error("Should not reach this point!", e);
		}
	}
}
