package milla.people_discount_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PeopleDiscountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeopleDiscountServiceApplication.class, args);
	}

}
