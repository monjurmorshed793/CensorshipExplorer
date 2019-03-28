package org.censorship.spring.domains.web.address;

import javax.persistence.*;

@Entity
@Table(name="web_address")
public class WebAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public WebAddress(){

    }

    public WebAddress(String name) {
        this.name = name;
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
}
