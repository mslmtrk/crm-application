package com.spring.model.input;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(@NotBlank String username, @NotBlank String password) {

}
