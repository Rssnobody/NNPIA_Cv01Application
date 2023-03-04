package cz.upce.cv01.repositories;

import cz.upce.cv01.DAOs.AppUser;
import cz.upce.cv01.DAOs.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAppUserRepository extends JpaRepository<AppUser, Long> {
    List<AppUser> findAllByActive(boolean active);

    @Query(
    """
    SELECT appUser
    FROM AppUser appUser
    JOIN appUser.roles roles
    WHERE roles = :role
    """)
    List<AppUser> findAllBySpecifiedRole(Role role);
}
