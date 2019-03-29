package org.censorship.spring.domains.isp;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IspProviderRepository extends JpaRepository<IspProvider, Long>{
    @Cacheable(value="ispProvider", key = "#id")
    IspProvider getById(Long id);
}
