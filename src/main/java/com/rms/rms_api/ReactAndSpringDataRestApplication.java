package com.rms.rms_api;

import java.net.URI;
import java.net.URISyntaxException;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ReactAndSpringDataRestApplication {

	public static void main(String[] args) throws URISyntaxException {// Start migration before execution
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
        Flyway flyway = new Flyway();
        flyway.setDataSource(dbUrl, username, password);
        flyway.migrate();
		SpringApplication.run(ReactAndSpringDataRestApplication.class, args);
		
	}
}