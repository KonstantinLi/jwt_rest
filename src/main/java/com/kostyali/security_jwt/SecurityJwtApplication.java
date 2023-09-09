package com.kostyali.security_jwt;

import com.kostyali.security_jwt.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityJwtApplication {
	public static void main(String[] args) {
		SpringApplication.run(SecurityJwtApplication.class, args);
	}
}
