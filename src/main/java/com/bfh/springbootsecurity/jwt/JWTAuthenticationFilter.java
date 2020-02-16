package com.bfh.springbootsecurity.jwt;

import com.bfh.springbootsecurity.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//exercise 4 -> JWT-Token-Filter-Servlet to validate a token from the authorization header
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    public JWTAuthenticationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(Constants.AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(Constants.JWT_BEARER)) {
            logger.debug("JWT token found in authorization header, try to validate");
            String jwtToken = request.getHeader(Constants.AUTHORIZATION_HEADER).replace(Constants.JWT_BEARER, "").trim();
            try {
                Authentication authentication = JWTTokenHelper.validateJWTToken(jwtToken);
                logger.debug("User [" + authentication.getName() + "] successfully authenticated");
                //set fully filled authentication object to the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (BadCredentialsException e) {
                logger.debug("Invalid JWT Token " + e.getMessage());
            }
        } else {
            logger.debug("No jwt token found in authorization header, pass to the next filter");
        }
        super.doFilterInternal(request, response, chain);
    }

}
