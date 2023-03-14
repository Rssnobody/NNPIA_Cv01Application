package cz.upce.cv01.repositories;

import cz.upce.cv01.domain.AppUser;
import cz.upce.cv01.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAppUserRepository extends JpaRepository<AppUser, Long> {
    List<AppUser> findAllByActive(boolean active);

    AppUser findByUserName(String userName);

    @Query(
    """
    SELECT appUser
    FROM AppUser appUser
    JOIN appUser.roles roles
    WHERE roles = :role
    """)
    List<AppUser> findAllBySpecifiedRole(Role role);
}
