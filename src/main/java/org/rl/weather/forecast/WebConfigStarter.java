package org.rl.weather.forecast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan
public class WebConfigStarter extends WebMvcConfigurerAdapter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index.html");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/static/");
		resolver.setSuffix(".html");
		return resolver;
	}

	@Bean
	public Connection getDbConnection() throws SQLException, IOException {
		Path h2File = Paths.get(System.getProperty("java.io.tmpdir"), "weather-forecast.tmp").normalize();
		Connection con = DriverManager.getConnection("jdbc:h2:file:" + h2File, "sa", "sa");

		return con;
	}

	public static void main(String[] args) {
		SpringApplication.run(WebConfigStarter.class, args);
	}
}
