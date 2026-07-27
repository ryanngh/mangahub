package com.ryan.novelhub.auth;

import com.ryan.novelhub.auth.dto.AuthResponse;
import com.ryan.novelhub.auth.dto.LoginRequest;
import com.ryan.novelhub.auth.dto.RegisterRequest;
import com.ryan.novelhub.auth.exception.InvalidCredentialsException;
import com.ryan.novelhub.user.User;
import com.ryan.novelhub.user.UserRepository;
import com.ryan.novelhub.user.dto.UserResponse;
import com.ryan.novelhub.user.exception.EmailAlreadyExistsException;
import com.ryan.novelhub.user.exception.UsernameAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder encoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException(request.username());
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        String hashedPassword = encoder.encode(request.password());
        User user = new User(request.username(), request.email(), hashedPassword);
        User saved = userRepository.save(user);

        String token = jwtService.generationToken(saved);
        return new AuthResponse(token, UserResponse.from(saved));
    }


    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(InvalidCredentialsException::new);
        if ((!encoder.matches(request.password(), user.getPasswordHash()))) {
            throw new InvalidCredentialsException();
        }
        String token = jwtService.generationToken(user);
        return new AuthResponse(token, UserResponse.from(user));
    }
}
