package com.bfh.springbootsecurity.jwt;

import com.bfh.springbootsecurity.config.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

//exercise 4 -> JWT-Token helper class to generate and validate a token
@Component
public final class JWTTokenHelper {
    private static Logger logger = LoggerFactory.getLogger(JWTTokenHelper.class);

    //exercise 4 -> Generate a token from a User object
    public static String generateJWTToken(UserDetails user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(joining(",")));
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, Constants.JWT_SECRET).compact();
        return token;
    }

    //exercise 4 -> validate a User from a jwt token
    private static UserDetails getUserDetailsFromToken(String jwtToken) throws BadCredentialsException {
        try {
            Claims body = Jwts.parser().setSigningKey(Constants.JWT_SECRET).parseClaimsJws(jwtToken).getBody();
            String[] authorities = ((String) body.get("authorities")).split(",");
            return new User(body.getSubject(), jwtToken, Arrays.stream(authorities).map(SimpleGrantedAuthority::new).collect(toList()));
        } catch (Exception e) {
            throw new BadCredentialsException("Unable to validate jwt token: " + e.getMessage());
        }
    }

    //exercise 4 -> validate a User from a jwt token and return an authentication object
    public static Authentication validateJWTToken(String jwtToken) throws BadCredentialsException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        if ((jwtToken != null) || (jwtToken.length() == 0) ) {
            UserDetails user = JWTTokenHelper.getUserDetailsFromToken(jwtToken);
            usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, jwtToken, user.getAuthorities());
        } else {
            throw new BadCredentialsException("Invalidate jwt token");
        }

        return usernamePasswordAuthenticationToken;
    }

}
