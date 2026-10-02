package com.makers.creditos;

import com.makers.creditos.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class MakersCreditosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MakersCreditosApiApplication.class, args);
	}

}
