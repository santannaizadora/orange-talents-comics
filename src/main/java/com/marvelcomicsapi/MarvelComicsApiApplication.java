package com.marvelcomicsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MarvelComicsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarvelComicsApiApplication.class, args);
	}

}
