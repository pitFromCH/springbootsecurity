package com.bfh.springbootsecurity.domain;

import com.bfh.springbootsecurity.repository.UserAdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserAdminService {

    private static Logger logger = LoggerFactory.getLogger(UserAdminService.class);

    //exercise 2 -> inject password encoder
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        //exercise 2 -> hashing the password
        long t1 = System.currentTimeMillis();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        long t2 = System.currentTimeMillis();
        logger.debug("BCrypt hashing-Time [ms]: " + (t2-t1));
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

}
