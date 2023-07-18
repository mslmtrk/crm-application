package com.spring.controller;

import com.spring.model.output.JwtResponse;
import com.spring.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.model.input.AuthenticationRequest;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signIn(@Valid @RequestBody AuthenticationRequest authenticationRequest) {

        return ResponseEntity.ok(userService.signIn(authenticationRequest));
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@Valid @RequestBody AuthenticationRequest authenticationRequest) {

        userService.registerUser(authenticationRequest);
    }

}