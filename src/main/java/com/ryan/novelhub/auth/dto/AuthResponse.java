package com.ryan.novelhub.auth.dto;

import com.ryan.novelhub.user.dto.UserResponse;

public record AuthResponse(
        String token,
        UserResponse user
) { }
