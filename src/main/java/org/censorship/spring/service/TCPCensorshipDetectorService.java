package org.censorship.spring.service;

import org.censorship.spring.domains.packet.information.PacketInformationRepository;
import org.censorship.spring.domains.web.address.WebAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TCPCensorshipDetectorService {

    private final Logger log = LoggerFactory.getLogger(TCPCensorshipDetectorService.class);

    private PacketInformationRepository packetInformationRepository;
    private WebAddressRepository webAddressRepository;
    private DNSCensorshipDetectorService dnsCensorshipDetectorService;



    private static final String TU_KEY = TCPCensorshipDetectorService.class.getName() + ".tu";
    private static final int TU = Integer.getInteger(TU_KEY, 4000); // [bytes]

    private static final String MTU_KEY = TCPCensorshipDetectorService.class.getName() + ".mtu";
    private static final int MTU = Integer.getInteger(MTU_KEY, 1403); // [bytes]


    private static final String COUNT_KEY = TCPCensorshipDetectorService.class.getName() + ".count";
    private static final int COUNT = 20000;

    private static final String READ_TIMEOUT_KEY = TCPCensorshipDetectorService.class.getName() + ".readTimeout";
    private static final int READ_TIMEOUT = Integer.getInteger(READ_TIMEOUT_KEY, 10); // [ms]

    private static final String SNAPLEN_KEY = TCPCensorshipDetectorService.class.getName() + ".snaplen";
    private static final int SNAPLEN = Integer.getInteger(SNAPLEN_KEY, 65536); // [bytes]


    public TCPCensorshipDetectorService(PacketInformationRepository packetInformationRepository, WebAddressRepository webAddressRepository, DNSCensorshipDetectorService dnsCensorshipDetectorService) {
        this.packetInformationRepository = packetInformationRepository;
        this.webAddressRepository = webAddressRepository;
        this.dnsCensorshipDetectorService = dnsCensorshipDetectorService;
    }


}
