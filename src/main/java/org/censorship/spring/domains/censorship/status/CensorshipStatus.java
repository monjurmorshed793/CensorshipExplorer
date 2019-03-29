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

    @Column(name="censorship_type")
    private CensorshipType censorshipType;

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

    public CensorshipType getCensorshipType() {
        return censorshipType;
    }

    public void setCensorshipType(CensorshipType censorshipType) {
        this.censorshipType = censorshipType;
    }

    public CensorshipType getOoniCensorshipType() {
        return ooniCensorshipType;
    }

    public void setOoniCensorshipType(CensorshipType ooniCensorshipType) {
        this.ooniCensorshipType = ooniCensorshipType;
    }

    @Override
    public String toString() {
        return "CensorshipStatus{" +
                "id=" + id +
                ", webAddressId=" + webAddressId +
                ", censorshipType=" + censorshipType +
                ", ooniCensorshipType=" + ooniCensorshipType +
                '}';
    }
}
