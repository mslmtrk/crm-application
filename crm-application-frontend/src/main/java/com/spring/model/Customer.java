package com.spring.model;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private Long id;

    private String firstName;

    private String lastName;

    @Email(message = "Email address must be valid.")
    private String email;
}
