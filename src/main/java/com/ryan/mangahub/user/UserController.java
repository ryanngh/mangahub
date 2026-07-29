package com.ryan.mangahub.user;

import com.ryan.mangahub.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(Authentication authentication) {
        // principal đã được JwtAuthFilter set là userId (Long)
        Long userId = (Long) authentication.getPrincipal();
        UserResponse response = userService.getProfile(userId);
        return ResponseEntity.ok(response);
    }
}