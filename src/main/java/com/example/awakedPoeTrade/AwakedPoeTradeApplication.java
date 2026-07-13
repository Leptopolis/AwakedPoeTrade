package com.example.awakedPoeTrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.awakedPoeTrade.controller.MainController;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class AwakedPoeTradeApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");
		ConfigurableApplicationContext context = SpringApplication.run(AwakedPoeTradeApplication.class, args);

		
	}

}
