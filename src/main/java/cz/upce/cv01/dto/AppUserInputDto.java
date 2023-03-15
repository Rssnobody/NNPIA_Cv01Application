package cz.upce.cv01.dto;

import cz.upce.cv01.domain.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserInputDto {
    @NotNull
    @NotBlank
    @Size(max = 256, min = 1)
    private String userName;

    private String password;

    private Boolean active;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    public AppUser toEntity() {
        return new AppUser(
                getUserName(),
                getPassword(),
                getActive(),
                getCreationDate(),
                getUpdateDate()
        );
    }

    public AppUser toEntity(final Long id) {
        return new AppUser(
                id,
                getUserName(),
                getPassword(),
                getActive(),
                getCreationDate(),
                getUpdateDate()
        );
    }
}
