package com.ftn.clinicCentre.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {
	
    @Value("${app.name}")
    private String APPLICATION_NAME;

    @Value("${token.secret}")
    public String TOKEN_SECRET;

    @Value("${token.access.duration}")
    private long ACCESS_TOKEN_DURATION;

    @Value("${token.refresh.duration}")
    private long REFRESH_TOKEN_DURATION;

    @Value("${token.passwordless-login.duration}")
    private long PASSWORDLESS_LOGIN_TOKEN_DURATION;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String generateAccessToken(UserDetails userDetails) {
    	Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + ACCESS_TOKEN_DURATION);
        return Jwts.builder()
                .setIssuer(APPLICATION_NAME)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .claim("roles", userDetails.getAuthorities())
                .signWith(SIGNATURE_ALGORITHM, TOKEN_SECRET).compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
    	Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + REFRESH_TOKEN_DURATION);
        return Jwts.builder()
                .setIssuer(APPLICATION_NAME)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .signWith(SIGNATURE_ALGORITHM, TOKEN_SECRET).compact();
    }

    public String generatePasswordlessLoginToken(UserDetails userDetails) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + PASSWORDLESS_LOGIN_TOKEN_DURATION);
        return Jwts.builder()
                .setIssuer(APPLICATION_NAME)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .signWith(SIGNATURE_ALGORITHM, TOKEN_SECRET).compact();
    }
    
    public String generateRegistrationToken(UserDetails userDetails) {
    	Date issuedAt = new Date();
        return Jwts.builder()
                .setIssuer(APPLICATION_NAME)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedAt)
                .signWith(SIGNATURE_ALGORITHM, TOKEN_SECRET).compact();
    	
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = getEmailFromToken(token);
        return (email != null && email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(TOKEN_SECRET)
                    .requireIssuer(APPLICATION_NAME)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String getEmailFromToken(String token) {
        String email;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            email = claims.getSubject();
        } catch (Exception e) {
            email = null;
        }
        return email;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }
}
