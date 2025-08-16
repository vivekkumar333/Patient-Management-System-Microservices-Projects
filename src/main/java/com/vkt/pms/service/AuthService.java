package com.vkt.pms.service;

import com.vkt.pms.entity.User;
import com.vkt.pms.exception.InvalidUserPasswordException;
import com.vkt.pms.exception.UserNotFoundException;
import com.vkt.pms.requestDto.LoginRequest;
import com.vkt.pms.utility.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Optional<String> authenticate(LoginRequest req) {
        User validatedUser = userService.getUserByEmail(req.getEmail())
                .filter(u-> passwordEncoder.matches(req.getPassword(),u.getPassword()))
                .orElseThrow(()-> new InvalidUserPasswordException("Invalid password"));
        return Optional.ofNullable(jwtUtil.generateToken(validatedUser));
    }

    public boolean isValidToken(String token){
        try{
            jwtUtil.validateToken(token);
            return true;
        }catch(JwtException ex){
            return false;
        }

    }
}
