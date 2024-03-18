package com.tlaxcala.microservices.config.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.Collections;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("jwt.secret")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtener el token JWT del encabezado de autorización
        String jwtToken = extractTokenFromHeader(request.getHeader("Authorization"));

        // Validar el token JWT
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            try {
                // Parsear y validar el token JWT
                @SuppressWarnings("deprecation")
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtSecret) // Debes usar tu clave secreta para validar el token
                        .parseClaimsJws(jwtToken.replace("Bearer ", ""))
                        .getBody();
                
                // Establecer la autenticación en el contexto de Spring Security
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        claims.getSubject(), null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Manejar la excepción de token inválido
                logger.error("Error al procesar el token JWT: {}");
            }
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    // Método para extraer el token del encabezado de autorización
    private String extractTokenFromHeader(String header) {
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}

