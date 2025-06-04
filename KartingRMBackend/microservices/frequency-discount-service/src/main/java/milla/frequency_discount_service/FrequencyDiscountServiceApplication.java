package milla.frequency_discount_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FrequencyDiscountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrequencyDiscountServiceApplication.class, args);
	}

}
