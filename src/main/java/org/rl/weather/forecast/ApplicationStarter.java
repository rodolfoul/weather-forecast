package org.rl.weather.forecast;

import org.springframework.boot.SpringApplication;

/**
 * Responsible for starting the application by starting spring boot.
 */
public class ApplicationStarter {
	public static void main(String[] args) {
		SpringApplication.run(WebConfig.class, args);
	}
}
