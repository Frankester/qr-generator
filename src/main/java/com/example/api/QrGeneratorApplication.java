package com.example.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title="QR Generator API", description = "Generate your own QRs of the links that you want", version = "1.0")
)
public class QrGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrGeneratorApplication.class, args);
	}

}
