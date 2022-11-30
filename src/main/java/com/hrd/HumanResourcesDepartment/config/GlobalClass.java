package com.hrd.HumanResourcesDepartment.config;

import com.hrd.HumanResourcesDepartment.models.Role;
import com.hrd.HumanResourcesDepartment.models.User;
import com.hrd.HumanResourcesDepartment.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class GlobalClass {

    @Autowired
    UserRepo usersRepo;

    protected User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            return usersRepo.findByUsername(userDetail.getUsername());
        }
        return null;
    }

    public Set<Role> getUserRole() {
        User user = getUser();
        return user.getRoles();
    }
}