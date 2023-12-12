package com.spring.service;

import com.spring.entity.Role;
import com.spring.entity.User;
import com.spring.model.input.AuthenticationRequest;
import com.spring.model.output.JwtResponse;
import com.spring.repository.RoleRepository;
import com.spring.repository.UserRepository;
import com.spring.security.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.EntityExistsException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void signIn_ValidAuthenticationRequest_ShouldReturnJwtResponse() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("username", "password");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtils.generateJwt(authentication)).thenReturn("mockJwt");
        when(jwtUtils.getExpirationFromJwt("mockJwt")).thenReturn(null);

        JwtResponse jwtResponse = userService.signIn(authenticationRequest);

        assertNotNull(jwtResponse);
        assertEquals("mockJwt", jwtResponse.getToken());
        assertNull(jwtResponse.getExpiryDate());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils, times(1)).generateJwt(authentication);
        verify(jwtUtils, times(1)).getExpirationFromJwt("mockJwt");
    }

    @Test
    void registerUser_NewUser_ShouldSaveUser() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("newUsername", "newPassword");
        when(userRepository.existsByUsername("newUsername")).thenReturn(false);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");

        Role roleUser = new Role(1L, "ROLE_USER");
        Role roleAdmin = new Role(2L, "ROLE_ADMIN");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(roleUser);
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(roleAdmin);

        userService.registerUser(authenticationRequest);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_ExistingUser_ShouldThrowEntityExistsException() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("existingUsername", "password");
        when(userRepository.existsByUsername("existingUsername")).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> userService.registerUser(authenticationRequest));
        verify(userRepository, never()).save(any(User.class));
    }
}
