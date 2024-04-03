package com.tlaxcala.microservices.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.tlaxcala.microservices.security.TokenValidator;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import com.netflix.zuul.context.RequestContext;

public class JwtFilter extends ZuulFilter {

    private TokenValidator tokenValidator;

    public JwtFilter(TokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

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
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        
        // Especifica aquí las condiciones bajo las cuales el filtro debe aplicarse
        // Por ejemplo, puedes excluir un endpoint específico
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/security")) {
            return false; // No aplicar el filtro en este endpoint
        }
        
        return true; // Aplicar el filtro en todos los demás casos
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);
        } else {
            // Si no se proporciona un token JWT, denegar el acceso
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            ctx.setSendZuulResponse(false);
            return null;
        }
        
        if (!tokenValidator.validateToken(jwtToken)) {
            // Si el token JWT no es válido, denegar el acceso
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            ctx.setSendZuulResponse(false);
            return null;
        }
        
        // Si el token JWT es válido, permitir el acceso
        return null;
    }
}