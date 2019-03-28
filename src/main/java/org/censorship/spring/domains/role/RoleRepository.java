package org.censorship.spring.domains.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
