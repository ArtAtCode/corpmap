package edu.scu.corpmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class CorpmapApplication {

	public static void main(String[] args) {
		System.err.println(System.getProperty("java.class.path"));
		SpringApplication.run(CorpmapApplication.class, args);
	}
}
