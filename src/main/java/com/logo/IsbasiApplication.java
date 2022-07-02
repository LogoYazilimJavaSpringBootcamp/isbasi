package com.logo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IsbasiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsbasiApplication.class, args);
	}

}
