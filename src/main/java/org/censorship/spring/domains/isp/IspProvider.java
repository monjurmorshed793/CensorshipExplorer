package org.censorship.spring.domains.isp;

import org.censorship.spring.enums.ProviderType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "isp_provider")
public class IspProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    @NotNull
    private String name;

    @Column(name="description")
    @NotNull
    private String description;

    @Column(name="provider_type")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private ProviderType providerType;

    public IspProvider() {
    }

    public ProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "IspProvider{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
