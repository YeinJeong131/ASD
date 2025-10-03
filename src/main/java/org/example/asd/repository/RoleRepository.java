//springframework data for roles, finding by the name of the role (user or admin)


package org.example.asd.repository;

import org.example.asd.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
