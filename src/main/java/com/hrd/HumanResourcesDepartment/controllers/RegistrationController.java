package com.hrd.HumanResourcesDepartment.controllers;

import com.hrd.HumanResourcesDepartment.models.Role;
import com.hrd.HumanResourcesDepartment.models.User;
import com.hrd.HumanResourcesDepartment.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepo userRepo;

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/registration")
    public String registration(){

        return "registration";
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping ("/registration")
    public String addUser(User user, Map<String, Object> model){
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if(userFromDb != null){
            model.put("message", "Такой пользователь уже существует!");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);

        return "redirect:/user/navigationAdmin";
    }

//    @GetMapping("/about") //страница о себе, о компании или продукте HRD
//    public String about(Model model) {
//        model.addAttribute("title", "about");
//        return "about";
//    }
}
