package cz.upce.cv01.DAOs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Data
@Entity
public class Role {
    @Id
    private Long id;
    @Column
    private String name;

    @ManyToMany
    @JoinTable(
            name="app_user_role",
            joinColumns = @JoinColumn(name="role_id"),
            inverseJoinColumns = @JoinColumn(name="app_user_id"))
    @JsonBackReference
    private List<AppUser> users = Collections.emptyList();
}
