package com.tlaxcala.microservices.gateway;

import com.tlaxcala.microservices.gateway.filter.*;
import com.tlaxcala.microservices.security.TokenValidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@ComponentScan(basePackages = "com.tlaxcala.microservices")
public class ZuulGatewayServer {
    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayServer.class, args);
    }

    @Bean
    public JwtFilter jwtFilter(TokenValidator tokenValidator) {
        return new JwtFilter(tokenValidator);
    }

    @Bean
    public PreFilter preFilter(){
        return new PreFilter();
    }

    @Bean
    public PostFilter postFilter(){
        return new PostFilter();
    }

    @Bean
    public ErrorFilter errorFilter(){
        return new ErrorFilter();
    }

    @Bean
    public RouteFilter routeFilter(){
        return new RouteFilter();
    }


}
