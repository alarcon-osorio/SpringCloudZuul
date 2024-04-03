package com.tlaxcala.microservices.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Component
public class TokenValidator {

    @Value("${jwt.secret}")
    private String secret;  // La misma clave secreta que utilizaste en el microservicio A

    public boolean validateToken(String jwtToken) {
        try {

            @SuppressWarnings("deprecation")
            Jws<Claims> claims = Jwts.parser()
                                .setSigningKey(secret)
                                .parseClaimsJws(jwtToken);
            
            // Verificar aquí si el token tiene un rol específico en sus afirmaciones
            // Por ejemplo, puedes obtener los roles del token y comprobar si contiene un cierto rol
            
            return true; // Si la validación es exitosa
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Si la validación falla
        }
    }
}


