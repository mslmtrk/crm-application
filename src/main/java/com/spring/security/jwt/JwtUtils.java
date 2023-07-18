package com.spring.security.jwt;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.spring.payload.JwtResponse;
import com.spring.security.services.UserDetailsImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

	@Value("${crm.app.jwtSecret}")
    private String jwtSecret;
    @Value("${crm.app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public JwtResponse generateJwt(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        String jwt = Jwts.builder()
            .setSubject(userPrincipal.getUsername())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
        
        return new JwtResponse(jwt, authentication.getName(), expiryDate);
    }
    public String getUsernameFromJwt(String token) {
        
        return Jwts.parser().setSigningKey(jwtSecret)
        		.parseClaimsJws(token).getBody()
        		.getSubject();
    }
    public boolean validateJwt(String token) {
        try {
        	
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
            
        } catch (Exception ex) {
        	
        	return false;
        }
    }
}
