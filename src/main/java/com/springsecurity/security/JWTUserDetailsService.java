package com.springsecurity.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * JWTUserDetailsService implements the Spring Security UserDetailsService interface.
 * It overrides the loadUserByUsername for fetching user details from the database using the username.
 */
@Service
public class JWTUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin@user.com".equals(username)) {
            return new User("admin@user.com", "$2a$10$P07xhbBiokCGKYwRvm7K3.qSoH1N.yBnpRSMZs/HnywvgYtXa9MCG",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}