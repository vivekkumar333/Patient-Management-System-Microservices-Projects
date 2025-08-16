package com.vkt.pms.service;

import com.vkt.pms.UserMapper;
import com.vkt.pms.entity.User;
import com.vkt.pms.enums.Role;
import com.vkt.pms.enums.Status;
import com.vkt.pms.exception.EmailAlreadyExistException;
import com.vkt.pms.exception.UserNotFoundException;
import com.vkt.pms.repository.UserRepository;
import com.vkt.pms.requestDto.UserRegistrationRequest;
import com.vkt.pms.responseDto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> getUserByEmail(String email) {

        User foundUser =  userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("Invalid Username! Not found"));
        return Optional.of(foundUser);
    }


    public Map<String,String> createUser(UserRegistrationRequest req) {
        Map<String, String> message = new HashMap<>();
        try {
            if (userRepository.findByEmail(req.getEmail()).isPresent()) {
                throw new EmailAlreadyExistException("User has already registered with this username: "+req.getEmail());
            }
            User user = User.builder()
                    .email(req.getEmail())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .role(Role.valueOf(req.getRole()))
                    .status(Status.ACTIVE)
                    .createdTimestamp(LocalDateTime.now())
                    .build();
            User savedUser = userRepository.save(user);
            if (!ObjectUtils.isEmpty(savedUser)) {
                message.put("msg", "User has registered successfully");
            }
        }catch (Exception ex){
            throw  new RuntimeException(ex.getLocalizedMessage());
        }
        return message;
    }

    public List<UserResponse> getUserByRole(String role) {
        return userRepository.findByRole(Role.valueOf(role))
                .orElseThrow(()-> new RuntimeException("No User found for user role: "+role))
                .stream()
                .map(UserMapper::userToUserResponseMapper)
                .collect(Collectors.toList());
    }
}
