package com.spring.service;

import com.spring.entity.Role;
import com.spring.entity.User;
import com.spring.model.input.AuthenticationRequest;
import com.spring.model.output.JwtResponse;
import com.spring.repository.RoleRepository;
import com.spring.repository.UserRepository;
import com.spring.security.jwt.JwtUtils;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Override
    public JwtResponse signIn(AuthenticationRequest authenticationRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.username(), authenticationRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwt(authentication);
        return new JwtResponse(jwt, authentication.getName(), jwtUtils.getExpirationFromJwt(jwt));
    }

    @Override
    public void registerUser(AuthenticationRequest authenticationRequest) {

        if (userRepository.existsByUsername(authenticationRequest.username())) {
            throw new EntityExistsException("Username is taken!");
        }

        String encodedPassword = passwordEncoder.encode(authenticationRequest.password());

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        roles.add(roleRepository.findByName("ROLE_ADMIN"));

        userRepository.save(
                User.builder()
                        .username(authenticationRequest.username())
                        .password(encodedPassword)
                        .roles(roles)
                        .build()
        );
    }
}
