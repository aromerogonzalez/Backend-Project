package com.fausto.users.users.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	@Value("${jwt_secret}")
    private String SECRET_KEY;

    @SuppressWarnings("null")
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !(authHeader.startsWith("Bearer "))) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authHeader.substring(7);
		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(SECRET_KEY.getBytes())
					.build()
					.parseClaimsJws(jwtToken)
					.getBody();

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(claims.getSubject(),
					null, null);
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authToken);
		} catch (Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}