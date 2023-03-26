package cz.upce.cv01.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import cz.upce.cv01.dto.AppUserOutputDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private Boolean active;
    @Column
    @EqualsAndHashCode.Exclude
    private LocalDateTime creationDate;
    @Column
    @EqualsAndHashCode.Exclude
    private LocalDateTime updateDate;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "author")
    @JsonManagedReference
    private List<Task> tasks = Collections.emptyList();

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "users")
    @JsonManagedReference
    private List<Role> roles = Collections.emptyList();

    public AppUser(String userName, String password, Boolean active, LocalDateTime creationDate, LocalDateTime updateDate) {
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public AppUser(Long id, String userName, String password, Boolean active, LocalDateTime creationDate, LocalDateTime updateDate) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public AppUserOutputDto toDto() {
        return new AppUserOutputDto(
                getId(),
                getUserName(),
                getPassword(),
                getActive(),
                getCreationDate(),
                getUpdateDate(),
                getRoles(),
                getTasks()
        );
    }
}
