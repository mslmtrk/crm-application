package com.spring.model.output;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class JwtResponse {

    private final String token;

    private final String username;

    private final Date expiryDate;
}
