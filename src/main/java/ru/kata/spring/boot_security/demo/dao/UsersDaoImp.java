//package ru.kata.spring.boot_security.demo.dao;
//
//
//import org.springframework.stereotype.Repository;
//import ru.kata.spring.boot_security.demo.entities.User;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//@Repository
//public class UsersDaoImp implements UsersDao {
//
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//
//    public List<User> getAllUsers() {
//
//        return entityManager.createQuery("from User", User.class).getResultList();
//    }
//
//
//    public User saveUser(User user) {
//        entityManager.persist(user);
//        entityManager.flush();
//        return user;
//    }
//
//
//    public User updateUser(User user) {
//        entityManager.merge(user);
//        entityManager.flush();
//        return user;
//    }
//
//
//    public User deleteUser(User user) {
//        if (entityManager.contains(user)) {
//            entityManager.remove(user);
//        } else {
//            entityManager.remove(entityManager.merge(user));
//        }
//        return user;
//    }
//
//
//    public User getUser(int count) {
//         return (User) entityManager.find(User.class, (long) count);
//    }
//
//
//}
