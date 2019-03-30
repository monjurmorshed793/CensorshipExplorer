package org.censorship.spring.domains.packet.information;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface PacketInformationRepository extends JpaRepository<PacketInformation, Long> {

    List<PacketInformation> getByLastModifiedBetween(Instant startTime, Instant endTime);
}
