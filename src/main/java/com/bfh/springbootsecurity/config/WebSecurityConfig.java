package com.bfh.springbootsecurity.config;

import com.bfh.springbootsecurity.jwt.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

//exercise 2 -> Define web security config (enable DEBUG, test only)
@Configuration
@EnableWebSecurity(debug = true)
//exercise 3 -> Enable method security
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //exercise 2 -> jdbc authentication use existing datasource (h2)
    @Autowired
    DataSource dataSource;

    //exercise 2 -> Configuration of an simple custom password encoder with a strength of 12 rounds
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    //exercise 2 -> Configuration of an inmemory and database authentication provider
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //exercise 2 -> inmemory authentication provider
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
                .withUser("user1@bfh.ch").roles(Constants.USER).password(passwordEncoder().encode("12345678")).and()
                .withUser("user2@bfh.ch").roles(Constants.USER, Constants.SUPERUSER).password(passwordEncoder().encode("12345678")).and()
                .withUser("admin@bfh.ch").roles(Constants.ADMINISTRATOR).password(passwordEncoder().encode("87654321"));

        //exercise 2 -> configure jdbc authentication provider (with default tables and default schema)
        auth.jdbcAuthentication().dataSource(dataSource);
    }


    //exercise 2 -> Configuration of the http object
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //exercise 2 -> 2 enforce BASIC AUTH for all resources and disable security for /console (csrf / frameOptions)
        http
                //exercise 2 -> all resources of the application
                .antMatcher("/**").authorizeRequests()
                    //exercise 3 ->  read is permitted for all (no authentication)
                    //.antMatchers(HttpMethod.GET, "/webcams/**").permitAll()
                    //exercise 3 -> Addon exercise: only authenticated user are permitted to read vip webcams
                    .antMatchers(HttpMethod.GET, "/webcams/**").access("isAnonymous() or isAuthenticated()")
                    //exercise 3 -> create webcams only by USER or SUPERUSER
                    .antMatchers(HttpMethod.POST, "/webcams/**").hasAnyRole(Constants.USER + "," + Constants.SUPERUSER)
                    //exercise 3 -> update webcams only by USER or SUPERUSER
                    .antMatchers(HttpMethod.PUT, "/webcams/**").hasAnyRole(Constants.USER + "," + Constants.SUPERUSER)
                    //exercise 3 -> delete webcams only by SUPERUSER
                    .antMatchers(HttpMethod.DELETE, "/webcams/**").hasRole(Constants.SUPERUSER)
                    //exercise 4  -> every authenticated user is allowed
                    .antMatchers( HttpMethod.GET, "/users/login/**").authenticated()
                    //exercise 3 -> User administration ony by ADMINISTRATOR
                    .antMatchers( "/users/**").hasRole(Constants.ADMINISTRATOR)
                    .anyRequest().authenticated()
                .and()
                    //exercise 4 -> add jwt autorization filter in chain
                    .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                    //exercise 2 -> HTTP BASIC authentication
                    .httpBasic()
                        .realmName("SecurityRealm")
                .and()
                //exercise 2 -> set X-Frame-Options from deny to sameorigin (X-Frame-Options: sameorigin)
                    .headers()
                        .frameOptions().sameOrigin()
                .and()
                    //exercise 2 -> disable csrf tokens for rest clients usage
                    .csrf().disable()
                    //exercise 2 -> define a stateless policy for microservices
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}

