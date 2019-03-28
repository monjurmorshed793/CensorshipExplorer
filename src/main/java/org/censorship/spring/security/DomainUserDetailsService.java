package org.censorship.spring.security;

import org.censorship.spring.domains.role.Role;
import org.censorship.spring.domains.role.RoleRepository;
import org.censorship.spring.domains.role.UserRole;
import org.censorship.spring.domains.role.UserRoleRepository;
import org.censorship.spring.domains.user.UserRepository;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);


    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private RoleRepository roleRepository;


    public DomainUserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.debug("Authenticating {}", login);
        return createSpringSecurityUser(login, userRepository.findByEmailAddress(login));

    }

    private User createSpringSecurityUser(String login, org.censorship.spring.domains.user.User user){
        UserRole userRole = userRoleRepository.findByUserId(user.getId());
        Role role = roleRepository.findById(userRole.getRoleId()).get();
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
        List<GrantedAuthority> grantedAuthorities = Arrays.asList(grantedAuthority);
        return new User(user.getEmailAddress(),  user.getPassword(), grantedAuthorities);
    }
}
