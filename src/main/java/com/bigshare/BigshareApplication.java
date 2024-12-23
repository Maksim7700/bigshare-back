package com.bigshare;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API", version = "2.0", description = "Login Information"))
@SecurityScheme(name = "Authorization", scheme = "Authorization", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, bearerFormat = "JWT")
public class BigshareApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigshareApplication.class, args);
	}
}
