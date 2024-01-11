package ru.kata.spring.boot_security.demo.entities;




import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name="roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users;


    public Role() {
    }
    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }



    @Override
    public String getAuthority() {
        return getName();
    }
}
