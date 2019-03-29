package org.censorship.spring;

import org.censorship.spring.domains.role.Role;
import org.censorship.spring.domains.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableCaching
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner loadData(RoleRepository roleRepository){
        return (args)->{
            List<Role> role = roleRepository.findAll();
            if(role.size()==0)
                roleRepository.save(new Role("USER"));
        };
    }

}
