package com.ryan.novelhub.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest (
        @NotBlank @Size(min = 3) String username,
        @NotBlank String password
){
}
