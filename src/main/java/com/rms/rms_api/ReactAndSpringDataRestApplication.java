package com.rms.rms_api;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ReactAndSpringDataRestApplication {

	public static void main(String[] args) {// Start migration before execution
		Flyway flyway = new Flyway();
        flyway.setDataSource(System.getenv("JDBC_DATABASE_URL"),
                             System.getenv("JDBC_DATABASE_USERNAME"),
                             System.getenv("JDBC_DATABASE_PASSWORD"));
        flyway.migrate();
		SpringApplication.run(ReactAndSpringDataRestApplication.class, args);
		
	}
}