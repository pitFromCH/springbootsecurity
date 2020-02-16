package com.bfh.springbootsecurity.repository;

import com.bfh.springbootsecurity.domain.WebCam;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WebCamRepository extends CrudRepository<WebCam, Long> {

    List<WebCam> findAllByOrderByIdAsc();
    List<WebCam> findAllByCountryOrderByIdAsc(String country);
    List<WebCam> findAllByCountryAndLocationOrderByIdAsc(String country, String location);
    List<WebCam> findAllByOwnerOrderByIdAsc(String owner);
    WebCam findWebCamById(Long id);
    WebCam findWebCamByIdAndOwner(Long id, String owner);

}
