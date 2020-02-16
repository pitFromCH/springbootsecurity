package com.bfh.springbootsecurity.repository;

import com.bfh.springbootsecurity.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserAdminRepository extends CrudRepository<User, String> {

    public List<User> findAllByOrderByIdAsc();
    public User findUserById(Long id);

}
