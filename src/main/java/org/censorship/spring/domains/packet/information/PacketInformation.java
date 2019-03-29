package org.censorship.spring.domains.packet.information;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="packet_information")
public class PacketInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String sourceAddress;
    private String destinationAddress;
    private Integer window;
    private Integer identificationNumber;
    private Integer sequenceNumber;
    private Integer sourcePort;
    private Integer destinationPort;
    private Integer acknowledgeNumber;
    private Integer ttl;
    private String syn;
    private String ack;
    private String fin;
    private String protocol;
    private Instant lastModified;

    public PacketInformation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public Integer getWindow() {
        return window;
    }

    public void setWindow(Integer window) {
        this.window = window;
    }

    public Integer getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(Integer identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Integer getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(Integer sourcePort) {
        this.sourcePort = sourcePort;
    }

    public Integer getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(Integer destinationPort) {
        this.destinationPort = destinationPort;
    }

    public Integer getAcknowledgeNumber() {
        return acknowledgeNumber;
    }

    public void setAcknowledgeNumber(Integer acknowledgeNumber) {
        this.acknowledgeNumber = acknowledgeNumber;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public String getSyn() {
        return syn;
    }

    public void setSyn(String syn) {
        this.syn = syn;
    }

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return "PacketInformation{" +
                "id=" + id +
                ", sourceAddress='" + sourceAddress + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", window=" + window +
                ", identificationNumber=" + identificationNumber +
                ", sequenceNumber=" + sequenceNumber +
                ", sourcePort=" + sourcePort +
                ", destinationPort=" + destinationPort +
                ", acknowledgeNumber=" + acknowledgeNumber +
                ", ttl=" + ttl +
                ", syn='" + syn + '\'' +
                ", ack='" + ack + '\'' +
                ", fin='" + fin + '\'' +
                ", protocol='" + protocol + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }
}
