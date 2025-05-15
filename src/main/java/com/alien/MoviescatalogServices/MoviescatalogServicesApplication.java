package com.alien.MoviescatalogServices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
@SpringBootApplication
@EnableDiscoveryClient
public class MoviescatalogServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviescatalogServicesApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	@Bean
	@LoadBalanced
	public WebClient.Builder builder(){
		return WebClient.builder();
	}
}
