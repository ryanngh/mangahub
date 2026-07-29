package com.ryan.mangahub.auth.dto;

import com.ryan.mangahub.user.dto.UserResponse;

public record AuthResponse(
        String token,
        UserResponse user
) { }
