//package ru.kata.spring.boot_security.demo.entities;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
//import ru.kata.spring.boot_security.demo.service.UsersServiceImp;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Component
//public class BeginClass {
//
//    private final UsersServiceImp usersServiceImp;
//    private final RoleRepository roleRepository;
//
//
//
//    @Autowired
//    public BeginClass(UsersServiceImp usersServiceImp, RoleRepository roleRepository) {
//        this.usersServiceImp = usersServiceImp;
//        this.roleRepository = roleRepository;
//        User user = new User("admin","admin", "12345");
//        Role role = new Role(1L,"ADMIN");
//        Set<Role> roles = new HashSet<>();
//        roles.add(role);
//        user.setRoles(List.of(role));
//        usersServiceImp.saveUser(user);
//
//
////        Role role2 = new Role(2L,"USER");
////        roleRepository.save(role);
//
//
//
//
//    }
//}
