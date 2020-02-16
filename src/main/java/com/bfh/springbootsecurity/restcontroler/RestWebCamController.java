package com.bfh.springbootsecurity.restcontroler;

import com.bfh.springbootsecurity.domain.WebCam;
import com.bfh.springbootsecurity.domain.WebCamService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping(value = "/webcams")
@RestController
public class RestWebCamController {

    private static Logger logger = LoggerFactory.getLogger(RestWebCamController.class);
    private WebCamService camService;

    @Autowired
    public RestWebCamController(WebCamService camService){
        this.camService = camService;
    }

    @ApiOperation(value = "Get all webcams", notes = "Returns a lists with all registered webcams")
    @GetMapping
    //exercise 3 -> Addon read ist permitted for anyone and for authenticated user
    @PreAuthorize("isAnonymous() or hasAnyRole('ROLE_USER', 'ROLE_SUPERUSER')")
    public List<WebCam> findAllWebCams() {
        logger.debug("Rest-API-Call: findAllWebCams()");
        return camService.findAllOderById();
    }

    @ApiOperation(value = "Register a new webcam", notes = "Returns the registered webcam")
    @PostMapping
    //exercise 3 -> update ist permitted for authenticated USER and SUPERUSER
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_SUPERUSER')")
    public WebCam createWebCam(@RequestBody WebCam webCam, Authentication authentication) {
        logger.debug("Rest-API-Call: createWebCam()");
        //exercise 2 -> insert authenticated user in field owner
        webCam.setOwner(authentication.getName());
        return  camService.createWebCam(webCam);
    }

    @ApiOperation(value = "Update (my) webcam", notes = "Returns updated webcam")
    @PutMapping(value = "/{id}")
    //exercise 3 -> update ist permitted for authenticated USER and SUPERUSER. check if authenticated user try to update own webcam
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_SUPERUSER') && (authentication.name==#webCam.owner)")
    public ResponseEntity updateWebCam(@PathVariable Long id, @NotNull @RequestBody WebCam webCam, Authentication authentication) {
        logger.debug("Rest-API-Call: updateWebCam()");
        WebCam cam = camService.findWebCamByIdAndOwner(id, webCam.getOwner());
        if (cam != null) {
            //exercise 2 -> insert authenticated user in field owner
            //exercise 3 -> could be solved with annotation @PreAuthorize
            //webCam.setOwner(authentication.getName());
            cam.updateWebCam(webCam);
            camService.updateWebCam(cam);
            return new ResponseEntity(cam, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete an exisiting webcam", notes = "Returns status code and deleted webcam (if found)")
    @DeleteMapping(value = "/{id}")
    //exercise 3 -> delete ist permitted for authenticated SUPERUSER
    @PreAuthorize("hasRole(T(com.bfh.springbootsecurity.config.Constants).SUPERUSER_AUTHORITY)")
    public ResponseEntity deleteWebCam(@PathVariable Long id) {
        logger.debug("Rest-API-Call: deleteWebCam");

        WebCam cam = camService.findById(id);
        if (cam != null) {
            camService.deleteWebCam(id);
            return new ResponseEntity(cam, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}
