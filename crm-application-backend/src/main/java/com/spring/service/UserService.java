package com.spring.service;

import com.spring.model.input.AuthenticationRequest;
import com.spring.model.output.JwtResponse;

public interface UserService {

    JwtResponse signIn(AuthenticationRequest authenticationRequest);

    void registerUser(AuthenticationRequest authenticationRequest);
}
