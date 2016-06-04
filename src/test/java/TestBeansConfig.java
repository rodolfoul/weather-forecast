import org.h2.tools.RunScript;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ComponentScan({"org.rl.weather.forecast.dao", "org.rl.weather.forecast.service"})
public class TestBeansConfig {
	@Bean
	public Connection getDbConnection() throws SQLException, IOException {
		Connection con = DriverManager.getConnection("jdbc:h2:mem:", "sa", "sa");

		try (Reader r = new InputStreamReader(getClass().getResourceAsStream("/db/create-db.sql"), StandardCharsets.UTF_8)) {
			RunScript.execute(con, r);
		} catch (IOException | SQLException e) {
			System.exit(1);
		}

		try (Reader r = new InputStreamReader(getClass().getResourceAsStream("/db/test-data.sql"), StandardCharsets.UTF_8)) {
			RunScript.execute(con, r);
		} catch (IOException | SQLException e) {
			System.exit(1);
		}

		return con;
	}
}
