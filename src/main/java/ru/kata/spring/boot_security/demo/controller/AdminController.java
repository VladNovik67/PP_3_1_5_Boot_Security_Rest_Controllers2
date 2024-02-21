package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UsersServiceImp;
import ru.kata.spring.boot_security.demo.util.UserErrorResponse;
import ru.kata.spring.boot_security.demo.util.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.util.UserNotFoundException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UsersServiceImp usersServiceImp;
    private final RoleServiceImp roleServiceImp;


    @Autowired
    public AdminController(UsersServiceImp usersServiceImp, RoleServiceImp roleServiceImp) {
        this.usersServiceImp = usersServiceImp;
        this.roleServiceImp = roleServiceImp;
    }

    @GetMapping()
    public List<User> getPeople() {
        return usersServiceImp.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") long id) {
        return usersServiceImp.findUserById(id);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException e) {
        UserErrorResponse response = new UserErrorResponse(
                "Person with this id wash't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedException e) {
        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid User user,
                                             BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new UserNotCreatedException(errorMsg.toString());
        }
        usersServiceImp.saveUser(user);

        //отправляем HTTP ответ с пустым телом и со статусом 200
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    //    @GetMapping("/")
//    public String show(@RequestParam(value = "id", required = false) long id, Model model) {
//        model.addAttribute("getUserId", usersServiceImp.findUserById(id));
//        return "admin/show";
//    }
//    @GetMapping()
//    public String showAll(ModelMap model, Principal principal) {
//        model.addAttribute("userss", usersServiceImp.getAllUsers());
//        model.addAttribute("loginUser", usersServiceImp.findByUsername(principal.getName()));
//        model.addAttribute("roles", roleServiceImp.findAllRoles());
//        model.addAttribute("userNew", new User());
//        return "admin/showAll";
//    }


//    @PostMapping
//    public String create(@ModelAttribute("user") User user) {
//        usersServiceImp.saveUser(user);
//        return "redirect:/admin";
//    }


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
}
