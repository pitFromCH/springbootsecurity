package com.bfh.springbootsecurity.restcontroler;

import com.bfh.springbootsecurity.config.Constants;
import com.bfh.springbootsecurity.domain.User;
import com.bfh.springbootsecurity.domain.UserAdminService;
import com.bfh.springbootsecurity.jwt.JWTTokenHelper;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RequestMapping(value = "/users")
@RestController
//exercise 3 -> All services of this REST controller must only be executed by an ADMINISTRATOR
@Secured(Constants.ADMINISTRATOR_AUTHORITY)
public class RestUserController {

    private static Logger logger = LoggerFactory.getLogger(RestUserController.class);
    private UserAdminService userAdminService;

    @Autowired
    public RestUserController(UserAdminService userAdminService){
        this.userAdminService = userAdminService;
    }

    @ApiOperation(value = "Get all users", notes = "Returns a lists with all registered users")
    @GetMapping
    public List<User> getUsers(Authentication authentication) {
        logger.debug("Rest-API-Call: getUsers()");
        //exercise 2 -> Log an authenticated user if exist
        //Instead of injection of the authentication object in the parameters, the object can be requested from the SecurityContextHolder
        //SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails user = (UserDetails) authentication.getPrincipal();
                Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) authentication.getAuthorities();
                logger.info("Authenticated User :  [" + authentication.getName() + "], Roles [" + authorities.toString() + "], Password [" + authentication.getCredentials() + "]");
            }
        }
        List<User> list = userAdminService.findAll();
        return userAdminService.findAll();
    }

    @ApiOperation(value = "Create a new user", notes = "Returns the created user")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.debug("Rest-API-Call: createUser()");
        User u = null;
        try {
            u = userAdminService.createUser(user);
        } catch (Exception e) {
            logger.debug("Error creating user: " + e.toString());
        }
        if (u != null) {
            return new ResponseEntity(user, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "users/login", notes = "Returns a jwt token on success")
    @GetMapping(value = "/login")
    @PreAuthorize("isAuthenticated()")
    //exercise 4 -> login service, anyone can execute, when authenticated
    public ResponseEntity login(Authentication authentication){
        logger.debug("Rest-API call: login");
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.FORBIDDEN);
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                //user is authenticated -> return a jwt token for authenticated user
                String jwtToken = JWTTokenHelper.generateJWTToken((UserDetails) authentication.getPrincipal());
                responseEntity = new ResponseEntity(jwtToken, HttpStatus.OK);
            }
        }
        return responseEntity;
    }

}
