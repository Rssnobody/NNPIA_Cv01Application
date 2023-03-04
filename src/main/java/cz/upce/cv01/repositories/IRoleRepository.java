package cz.upce.cv01.repositories;

import cz.upce.cv01.DAOs.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    @Query(
            """
            SELECT role
            FROM Role role
            WHERE role.name = :name
            """)
    Role findByName(String name);
}
