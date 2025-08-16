package com.vkt.pms.controller;

import com.vkt.pms.requestDto.UserRegistrationRequest;
import com.vkt.pms.responseDto.UserResponse;
import com.vkt.pms.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Map<String,String>> userRegistration(@RequestBody @Valid UserRegistrationRequest req) {
        return ResponseEntity.ok(userService.createUser(req));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponse>> getUserOfRole(@PathVariable("role") String role){
        return ResponseEntity.ok(userService.getUserByRole(role));
    }
}
