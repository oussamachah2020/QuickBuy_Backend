package com.example.QuickBuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.QuickBuy.models")
public class QuickBuyApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickBuyApplication.class, args);
	}

}
