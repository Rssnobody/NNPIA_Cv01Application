package cz.upce.cv01.DAOs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Task {
    @Id
    private Long id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private LocalDateTime creationDate;
    @Column
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name="author_id")
    @JsonBackReference
    private AppUser author;
}
