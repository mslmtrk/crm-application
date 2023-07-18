package com.spring.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerUpdateRequest(@NotNull Long id, @NotBlank String firstName, @NotBlank String lastName, @NotBlank String email) {

}
