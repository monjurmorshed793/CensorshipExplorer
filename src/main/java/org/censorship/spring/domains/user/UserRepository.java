package org.censorship.spring.domains.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAddress(String emailAddress);

    User findByEmailAddressAndPassword(String emailAddress, String password);
}
