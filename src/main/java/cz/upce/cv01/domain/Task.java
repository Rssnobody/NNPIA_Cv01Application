package cz.upce.cv01.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cz.upce.cv01.dto.TaskOutputDto;
import lombok.Data;

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

    public TaskOutputDto toDto() {
        return new TaskOutputDto(
                getId(),
                getTitle(),
                getDescription(),
                getCreationDate(),
                getUpdateDate()
        );
    }
}
