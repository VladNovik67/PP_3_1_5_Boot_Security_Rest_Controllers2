package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UsersServiceImp;

import java.security.Principal;


@RestController
@Secured({"ROLE_USER","ROLE_ADMIN"})
@RequestMapping("/api/users")
public class UserController {

    private final UsersServiceImp usersServiceImp;

    @Autowired
    public UserController(UsersServiceImp usersServiceImp) {
        this.usersServiceImp = usersServiceImp;
    }


    @GetMapping("/user")
    public ResponseEntity<User> getAuth(Principal principal) {

        User user = usersServiceImp.findByUsername(principal.getName());
//        System.out.println("000000000000000000000000000000000000000000000000000000000000-"+ user.getUsername());
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @GetMapping("/user")
//    public String show(Model model, Principal principal) {
//        model.addAttribute("userss", usersServiceImp.findByUsername(principal.getName()));
//        return "user/show";
//    }
}
