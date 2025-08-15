package com.example.api.services;

import com.example.api.models.User;
import com.example.api.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {


    private final UserRepo repo;

    @Autowired
    public UserService(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOp = this.repo.findByUsername(username);

        if(userOp.isEmpty()){
            throw new BadCredentialsException("Bad Credentials");
        }

        User user = userOp.get();

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
