package org.censorship.spring.domains.role;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByUserId(Long userId);
}
