package org.censorship.spring.domains.censorship.status;

import org.censorship.spring.enums.CensorshipType;

import javax.persistence.*;

@Entity
@Table(name = "censorship_status")
public class CensorshipStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="isp_provider_id")
    private Long ispProviderId;

    @Column(name = "web_address_id")
    private Long webAddressId;

    @Column(name="dns_censorship_type")
    private CensorshipType dnsCensorshipType;

    @Column(name="tcp_ip_censorship_type")
    private CensorshipType tcpIpCensorshipType;

    @Column(name="http_censorship_type")
    private CensorshipType httpCensorshipType;

    @Column(name="ooni_censorship_type")
    private CensorshipType ooniCensorshipType;

    public CensorshipStatus() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIspProviderId() {
        return ispProviderId;
    }

    public void setIspProviderId(Long ispProviderId) {
        this.ispProviderId = ispProviderId;
    }

    public Long getWebAddressId() {
        return webAddressId;
    }

    public void setWebAddressId(Long webAddressId) {
        this.webAddressId = webAddressId;
    }

    public CensorshipType getDnsCensorshipType() {
        return dnsCensorshipType;
    }

    public void setDnsCensorshipType(CensorshipType dnsCensorshipType) {
        this.dnsCensorshipType = dnsCensorshipType;
    }

    public CensorshipType getOoniCensorshipType() {
        return ooniCensorshipType;
    }

    public void setOoniCensorshipType(CensorshipType ooniCensorshipType) {
        this.ooniCensorshipType = ooniCensorshipType;
    }

    public CensorshipType getTcpIpCensorshipType() {
        return tcpIpCensorshipType;
    }

    public void setTcpIpCensorshipType(CensorshipType tcpIpCensorshipType) {
        this.tcpIpCensorshipType = tcpIpCensorshipType;
    }

    public CensorshipType getHttpCensorshipType() {
        return httpCensorshipType;
    }

    public void setHttpCensorshipType(CensorshipType httpCensorshipType) {
        this.httpCensorshipType = httpCensorshipType;
    }
}
