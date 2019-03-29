package org.censorship.spring.domains.censorship.status;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CensorshipStatusRepository extends JpaRepository<CensorshipStatus, Long> {

    List<CensorshipStatus> findAllByIspProviderId(Long ispProviderId);

    void deleteAllByIspProviderId(Long ispProviderId);

}
