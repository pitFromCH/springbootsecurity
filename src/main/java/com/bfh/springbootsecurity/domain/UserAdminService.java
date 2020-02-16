package com.bfh.springbootsecurity.domain;

import com.bfh.springbootsecurity.repository.UserAdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserAdminService {

    private static Logger logger = LoggerFactory.getLogger(UserAdminService.class);

    private UserAdminRepository userRepository;

    @Autowired
    public UserAdminService(UserAdminRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByIdAsc();
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

}
