package org.censorship.spring.service;

import org.censorship.spring.domains.packet.information.PacketInformation;
import org.censorship.spring.domains.packet.information.PacketInformationRepository;
import org.censorship.spring.domains.web.address.WebAddress;
import org.censorship.spring.domains.web.address.WebAddressRepository;
import org.pcap4j.core.*;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.TcpPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Transactional
    public void sniffWebAddress(List<WebAddress> webAddressList) throws Exception{
        PcapNetworkInterface networkInterface = detectNetworkDevices().get(0);
        PcapHandle handle = networkInterface.openLive(SNAPLEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
        ExecutorService pool = Executors.newSingleThreadExecutor();

        PacketListener listener = (packet->{
            if(packet.contains(TcpPacket.class) && packet.contains(IpV4Packet.class)){
                TcpPacket tcpPacket = packet.get(TcpPacket.class);
                IpV4Packet ipV4Packet = packet.get(IpV4Packet.class);
                insertIntoPacketInformation(tcpPacket, ipV4Packet);
            }
        });

        Task task = new Task(handle, listener);
        pool.execute(task);

        Socket clientSocket;

        for(WebAddress webAddress: webAddressList){
            String refactoredWebAddress = dnsCensorshipDetectorService.refactorWebAddress(webAddress.getName());

                if(webAddress.getName().contains("http://"))
                    clientSocket = new Socket(InetAddress.getByName(refactoredWebAddress),80);
                else if(webAddress.getName().contains("https://"))
                    clientSocket = new Socket(InetAddress.getByName(refactoredWebAddress), 443);
                else
                    clientSocket = new Socket(InetAddress.getByName(refactoredWebAddress),80);
                if(clientSocket.isConnected())
                    clientSocket.close();


        }

        if (handle != null && handle.isOpen()) {
            handle.close();
        }

        if (pool != null && !pool.isShutdown()) {
            pool.shutdown();
        }
    }

    public List<PcapNetworkInterface> detectNetworkDevices() throws Exception, PcapNativeException, NotOpenException {
        List<PcapNetworkInterface> allDevices = null;
        allDevices = Pcaps.findAllDevs();
        return allDevices;
    }

    @Transactional
    void insertIntoPacketInformation(TcpPacket tcpPacket, IpV4Packet ipV4Packet){
        PacketInformation packetInformation = new PacketInformation();
        packetInformation.setSourceAddress(ipV4Packet.getHeader().getSrcAddr().toString().replace("/",""));
        packetInformation.setDestinationAddress(ipV4Packet.getHeader().getDstAddr().toString().replace("/",""));
        packetInformation.setWindow(tcpPacket.getHeader().getWindowAsInt());
        packetInformation.setIdentificationNumber(ipV4Packet.getHeader().getIdentificationAsInt());
        packetInformation.setSequenceNumber(tcpPacket.getHeader().getSequenceNumber());
        packetInformation.setSourcePort(tcpPacket.getHeader().getSrcPort().valueAsInt());
        packetInformation.setDestinationPort(tcpPacket.getHeader().getDstPort().valueAsInt());
        packetInformation.setAcknowledgeNumber(tcpPacket.getHeader().getAcknowledgmentNumber());
        packetInformation.setTtl(ipV4Packet.getHeader().getTtlAsInt());
        packetInformation.setSyn(tcpPacket.getHeader().getSyn()==true?"true":"false");
        packetInformation.setFin(tcpPacket.getHeader().getFin()==true?"true":"false");
        packetInformation.setAck(tcpPacket.getHeader().getAck()==true?"true":"false");
        packetInformation.setProtocol(ipV4Packet.getHeader().getProtocol().valueAsString());
        packetInformation.setLastModified(new Date().toInstant());
        packetInformationRepository.save(packetInformation);
    }

    private static class Task implements Runnable {

        private PcapHandle handle;
        private PacketListener listener;

        public Task(PcapHandle handle, PacketListener listener) {
            this.handle = handle;
            this.listener = listener;
        }

        @Override
        public void run() {
            try {
                handle.loop(COUNT, listener);
            } catch (PcapNativeException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (NotOpenException e) {
                e.printStackTrace();
            }
        }
    }
}
