package com.hrd.HumanResourcesDepartment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hrd.HumanResourcesDepartment.models.User;

public interface UserRepo extends JpaRepository <User, Long>{
    User findByUsername(String username);
}
