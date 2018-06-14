package edu.scu.corpmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories("edu.scu.corpmap.repositories")
public class CorpmapApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorpmapApplication.class, args);
	}
}
