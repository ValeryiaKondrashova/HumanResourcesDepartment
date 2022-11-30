package com.hrd.HumanResourcesDepartment.service;

import com.hrd.HumanResourcesDepartment.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

//    private final UserRepo userRepo;
//
//    public UserService(UserRepo userRepo) {
//        this.userRepo = userRepo;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

}
