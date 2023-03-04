package cz.upce.cv01.DAOs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/*
* mozne hodnoty directiva spring.jpa.hibernate.ddl-auto:
*
* create:       Drops and creates the schema at the application startup.
*               With this option, all your data will be gone on each startup.
* create-drop:  Creates schema at the startup and destroys the schema on context closure.
*               Useful for unit tests.
* none:         No database Schema initialization.
* update:       Updates the schema only if necessary. For example, If a new field was added in an entity,
*               then it will simply alter the table for a new column without destroying the data.
* validate:     Only checks if the Schema matches the Entities. If the schema doesn't match,
*               then the application startup will fail. Makes no changes to the database.
*/

@Data
@Entity
public class AppUser {
    @Id
    private Long id;
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private boolean active;
    @Column
    private LocalDateTime creationDate;
    @Column
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "author")
    @JsonManagedReference
    private List<Task> tasks = Collections.emptyList();
    @ManyToMany(mappedBy = "users")
    @JsonManagedReference
    private List<Role> roles = Collections.emptyList();
}
