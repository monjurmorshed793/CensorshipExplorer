package org.censorship.spring.service;

import org.censorship.spring.domains.isp.IspProvider;
import org.censorship.spring.domains.isp.IspProviderRepository;
import org.censorship.spring.domains.packet.information.PacketInformation;
import org.censorship.spring.domains.packet.information.PacketInformationRepository;
import org.censorship.spring.domains.web.address.WebAddress;
import org.censorship.spring.domains.web.address.WebAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CensorshipStatusService {
    @Autowired
    private WebAddressRepository webAddressRepository;
    @Autowired
    private IspProviderRepository ispProviderRepository;
    @Autowired
    private PacketInformationRepository packetInformationRepository;
    @Autowired
    private TCPCensorshipDetectorService tcpCensorshipDetectorService;

    public void checkCensorshipStatus(IspProvider ispProvider) throws Exception{
        Instant startTime = Instant.now();

        List<WebAddress> webAddressList = webAddressRepository.findAll();
        tcpCensorshipDetectorService.sniffWebAddress(webAddressList);

        List<PacketInformation> packetInformationList = packetInformationRepository.getByLastModifiedBetween(startTime,  Instant.now());



    }

}
