package com.bfh.springbootsecurity.restcontroler;

import com.bfh.springbootsecurity.domain.User;
import com.bfh.springbootsecurity.domain.UserAdminService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping(value = "/users")
@RestController
public class RestUserController {

    private static Logger logger = LoggerFactory.getLogger(RestUserController.class);
    private UserAdminService userAdminService;

    @Autowired
    public RestUserController(UserAdminService userAdminService){
        this.userAdminService = userAdminService;
    }

    @ApiOperation(value = "Get all users", notes = "Returns a lists with all registered users")
    @GetMapping
    public List<User> getUsers() {
        logger.debug("Rest-API-Call: getUsers()");
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

}
