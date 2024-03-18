package com.tlaxcala.microservices.gateway.filter;

import javax.servlet.http.HttpServletRequest;
import com.netflix.zuul.context.RequestContext;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.netflix.zuul.ZuulFilter;

import org.springframework.security.core.Authentication;



@Component
public class JwtPropagationFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        // Debes decidir en qué condiciones deseas aplicar este filtro
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        
        // Aquí obtienes el token JWT de alguna manera, por ejemplo, desde el contexto de seguridad
        String jwtToken = obtainJwtTokenFromSecurityContext();

        // Agregar el token JWT al encabezado de autorización
        if (jwtToken != null) {
            ctx.addZuulRequestHeader("Authorization", "Bearer " + jwtToken);
        }

        return null;
    }

    private String obtainJwtTokenFromSecurityContext() {
        
        // Obtener la autenticación del contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Verificar si la autenticación es del tipo UsernamePasswordAuthenticationToken
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            // Extraer el token JWT de la autenticación
            UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
            Object credentials = authToken.getCredentials();
            
            // Verificar si las credenciales son un token JWT (en este ejemplo, suponemos que lo son)
            if (credentials instanceof String) {
                return (String) credentials;
            }
        }
        
        // Si no se puede obtener el token JWT del contexto de seguridad, retornar null
        return null;
    }
}
