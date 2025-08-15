package com.example.api.services;

import com.example.api.exceptions.UserAlreadyExistsException;
import com.example.api.models.User;
import com.example.api.models.dto.JwtResponse;
import com.example.api.models.dto.LoginDTO;
import com.example.api.models.dto.RegisterDTO;
import com.example.api.repositories.UserRepo;
import com.example.api.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepo repo;


    private final JwtUtil jwtUtils;


    private final AuthenticationManager authManager;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserRepo repo, JwtUtil jwtUtils, AuthenticationManager authManager, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.jwtUtils = jwtUtils;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterDTO registerReq) throws UserAlreadyExistsException {
        if(this.repo.existsByUsername(registerReq.getUsername())){
            throw new UserAlreadyExistsException("User with username \""+ registerReq.getUsername()+"\" already exists" );
        }


        User user = new User();

        user.setUsername(registerReq.getUsername());
        user.setEmail(registerReq.getEmail());
        user.setPassword(passwordEncoder.encode(registerReq.getPassword()));

        this.repo.save(user);

    }

    public JwtResponse login(LoginDTO login) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
        );

        String jwt = jwtUtils.createTokenJwt(auth);

        return new JwtResponse(jwt);
    }
}
