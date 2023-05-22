package com.example.pokoponsproj.filters;

import com.auth0.jwt.JWT;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(2) // the order to run this filter in the filter chain
public class TokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = request.getHeader("authorization");
            String email = JWT.decode(token.replace("Bearer ", "")).getClaim("email").asString();
            // all is well, move on
            filterChain.doFilter(request, response); // move to next filter on the chain
        }catch (Exception e){
            response.setStatus(401);
            response.getWriter().write("Unauthorized, please log in!");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().startsWith("/auth");
    }
}

