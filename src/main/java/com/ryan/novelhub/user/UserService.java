package com.ryan.novelhub.user;


import com.ryan.novelhub.user.dto.UserResponse;
import com.ryan.novelhub.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserResponse getProfile(Long id) {
        return UserResponse.from(getById(id));
    }
}
