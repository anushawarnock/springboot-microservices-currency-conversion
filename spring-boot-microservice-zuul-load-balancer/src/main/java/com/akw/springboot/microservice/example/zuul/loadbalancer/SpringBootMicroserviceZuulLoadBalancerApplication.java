package com.akw.springboot.microservice.example.zuul.loadbalancer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@EnableZuulProxy
@SpringBootApplication
public class SpringBootMicroserviceZuulLoadBalancerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMicroserviceZuulLoadBalancerApplication.class, args);
	}
}
