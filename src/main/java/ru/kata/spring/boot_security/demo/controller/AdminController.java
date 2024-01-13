package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UsersServiceImp;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsersServiceImp usersServiceImp;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UsersServiceImp usersServiceImp, RoleRepository roleRepository) {
        this.usersServiceImp = usersServiceImp;
        this.roleRepository = roleRepository;
    }


    @GetMapping("/")
    public String show(@RequestParam(value = "id", required = false) long id, Model model) {
        model.addAttribute("getUserId", usersServiceImp.findUserById(id));
        return "admin/show";
    }
    @GetMapping()
    public String showAll(ModelMap model) {
        model.addAttribute("userss", usersServiceImp.getAllUsers());
        return "admin/showAll";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
//        User user1 = new User();
//        ModelAndView mav = new ModelAndView("user_form");
//        modelAndView.addObject("user", new User());
//
//        List<Role> roles = (List<Role>) roleRepository.findAll();
//
//        modelAndView.addObject("allRoles", roles);
        model.addAttribute("roles", roleRepository.findAll());

        return "admin/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user, Model model) {
        usersServiceImp.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", usersServiceImp.findUserById(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        usersServiceImp.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        usersServiceImp.deleteUser(usersServiceImp.findUserById(id));
        return "redirect:/admin";
    }







//    @GetMapping("/")
//    public String pageForAdmin(Principal principal) {
//        return "aaaaaaaaaaaaaaaaaaaaa"+" "+principal.getName();
//    }
//
//    @GetMapping("/read_profile")
//    public String pageReadProfile(Principal principal) {
//        return "Profile is " + principal.getName();
//    }
//
//    @GetMapping("/authenticated")
//    public String pageForAuthenticatedUsers(Principal principal) {
//        User user = usersServiceImp.ffindByUserName(principal.getName());
//        return "Secured page for web service "+ user.getUsername()+ " " +user.getPassword();
////        return "admin/authenticated";
//    }
}
