package com.bfh.springbootsecurity.domain;

import com.bfh.springbootsecurity.repository.WebCamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WebCamService {
    private WebCamRepository webCamRepository;

    @Autowired
    public WebCamService(WebCamRepository webCamRepository){
        this.webCamRepository = webCamRepository;
    }

    public List<WebCam> findAllOderById() {
        return webCamRepository.findAllByOrderByIdAsc();
    }

    public WebCam findById(Long id) {
        return webCamRepository.findWebCamById(id);
    }

    public List<WebCam> findAllByCountryAsc(String country) {
        return webCamRepository.findAllByCountryOrderByIdAsc(country);
    }

    public List<WebCam> findAllByCountryAndLocationAsc(String country, String location) {
        return webCamRepository.findAllByCountryAndLocationOrderByIdAsc(country, location);
    }

    public List<WebCam> findAllByOwner(String owner) {
        return webCamRepository.findAllByOwnerOrderByIdAsc(owner);
    }

    public WebCam findWebCamById(Long id) {
        return webCamRepository.findWebCamById(id) ;
    }

    public WebCam findWebCamByIdAndOwner(Long id, String owner) {
        return webCamRepository.findWebCamByIdAndOwner(id, owner) ;
    }

    public WebCam updateWebCam(WebCam webCam) {
        return webCamRepository.save(webCam);
    }

    public WebCam createWebCam(WebCam webCam) {
        return webCamRepository.save(webCam);
    }

    public void deleteWebCam(Long id) {
        webCamRepository.deleteById(id);
    }
}
