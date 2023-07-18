package com.spring.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotEmpty(message = "Username is required.")
    @Size(max = 30, message = "Username must not exceed 30 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,}$", message = "Username must consists of alphanumeric characters and must be at least 4 characters long.")
    private String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, max = 40, message = "Password must not exceed 40 characters.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password must contain at least one number, one uppercase and lowercase letter and must be at least 8 characters long.")
    private String password;
}
