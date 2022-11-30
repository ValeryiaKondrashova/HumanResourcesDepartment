package com.hrd.HumanResourcesDepartment.controllers;

import com.hrd.HumanResourcesDepartment.models.Employee;
import com.hrd.HumanResourcesDepartment.models.Role;
import com.hrd.HumanResourcesDepartment.models.User;
import com.hrd.HumanResourcesDepartment.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepo userRepository;

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/navigationAdmin")
    public String navigationAdmin(Model model) {
        model.addAttribute("title", "Список пользователей");

        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users); // в кавычках означает - передаем по имени users

        return "navigationAdmin";
    }

    @GetMapping("/editUsers")
    public String editUsers(Model model) {
        model.addAttribute("title", "Редактирование пользователей");

        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users); // в кавычках означает - передаем по имени users

        return "editUsers";
    }

    @GetMapping("/removeUsers")
    public String removeUsers(Model model) {
        model.addAttribute("title", "Удаление пользователей");

        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users); // в кавычках означает - передаем по имени users

        return "removeUsers";
    }


    @PostMapping("/filterUser")
    public String filterUser(@RequestParam String filterUser, Map<String, Object> model){

        if(filterUser != null && !filterUser.isEmpty()){
            User users = userRepository.findByUsername(filterUser);
            model.put("users", users);
        }
        else{
            Iterable<User> usersAll = userRepository.findAll();
            model.put("users", usersAll);
        }

        return "navigationAdmin";
    }

    @GetMapping("/user/{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roless", Role.values());

        return "userEdit";
    }

//    @Transactional
    @PostMapping("/user/{user}")
    public String userSave(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Set<Role> roles,
            @RequestParam("userId") User user,
            Map<String, Object> model
    ){

        //System.out.println("User has been posted! before");
        //System.out.println("User has been posted!" + user + " --- 1/roles = " + user.getRoles());
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles);

//        Set<String> roles = Arrays.stream(Role.values())
//                        .map(Role::name)
//                        .collect(Collectors.toSet());
//
//        user.getRoles().clear();
//
//        for(String key : form.keySet()){
//            if(roles.contains(key)){
//                user.getRoles().add(Role.valueOf(key));
//            }
//        }

        System.out.println("User has been posted! after");
        System.out.println("User has been posted!" + user + " --- 2/roles = " + user.getRoles());

        userRepository.save(user);

        System.out.println("User has been posted! final");

        return "redirect:/user/navigationAdmin";
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping("/addUser")
//    public String addUser(Model model) {
//        model.addAttribute("title", "Добавить пользователя");
//
//        return "registration";
//    }

    @PostMapping("/user/{id}/delete")
    public String userDelete(@PathVariable(value = "id") Long id){

        userRepository.deleteById(id);

        return "redirect:/user/navigationAdmin";
    }
}

