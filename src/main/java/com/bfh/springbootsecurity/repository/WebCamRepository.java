package com.bfh.springbootsecurity.repository;

import com.bfh.springbootsecurity.domain.WebCam;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WebCamRepository extends CrudRepository<WebCam, Long> {

    //exercise 3 -> Addon exercise: only authenticated user are permitted to read vip webcams
    @Query("select wc from WebCam wc where wc.isVip = false or 1 = ?#{hasAnyRole('ROLE_USER', 'ROLE_SUPERUSER') ? 1 : 0}")
    List<WebCam> findAllByOrderByIdAsc();
    List<WebCam> findAllByCountryOrderByIdAsc(String country);
    List<WebCam> findAllByCountryAndLocationOrderByIdAsc(String country, String location);
    List<WebCam> findAllByOwnerOrderByIdAsc(String owner);
    WebCam findWebCamById(Long id);
    WebCam findWebCamByIdAndOwner(Long id, String owner);

}
