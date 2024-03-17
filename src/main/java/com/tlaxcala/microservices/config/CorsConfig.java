package com.tlaxcala.microservices.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer{

    @Value("${app.addMappString}")
    private String addMappString;

    @Value("${app.allowedOrigins}")
    private String[] allowedOrigins;

    @Value("${app.allowedMethods}")
    private String[] allowedMethods;

    @Value("${app.allowedHeaders}")
    private String[] allowedHeaders;

    @SuppressWarnings("null")
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(addMappString)
                .allowedOrigins(allowedOrigins)
                .allowedMethods(allowedMethods)
                .allowedHeaders(allowedHeaders);
    }

    /* 
    En este ejemplo:
    allowedOrigins("*"): Permite solicitudes desde cualquier origen. Puedes especificar los orígenes permitidos aquí si lo deseas.
    allowedMethods("GET", "POST", "PUT", "DELETE"): Define los métodos HTTP permitidos.
    allowedHeaders("*"): Permite todos los encabezados en las solicitudes.
    */
    
}
