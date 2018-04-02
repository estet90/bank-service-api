package ru.kononov.tinkoffbank.bankservices.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@Configuration
public class PersistenceConfig {

	@Bean
	@ConfigurationProperties
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().build();
	}

}
