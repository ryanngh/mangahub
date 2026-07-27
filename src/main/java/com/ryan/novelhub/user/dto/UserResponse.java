package com.ryan.novelhub.user.dto;

import com.ryan.novelhub.user.Role;
import com.ryan.novelhub.user.User;

public record UserResponse (
        Long id,
        String username,
        String email,
        String displayName,
        String avatarUrl,
        Role role
){
    public static UserResponse from(User user){
        return new UserResponse(
          user.getId(),
          user.getUsername(),
          user.getEmail(),
          user.getDisplayName(),
          user.getAvatarUrl(),
          user.getRole()
        );
    }
}
