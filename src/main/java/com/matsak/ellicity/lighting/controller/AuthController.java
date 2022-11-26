package com.matsak.ellicity.lighting.controller;

import com.matsak.ellicity.lighting.entity.User;
import com.matsak.ellicity.lighting.exceptions.BadRequestException;
import com.matsak.ellicity.lighting.payload.*;
import com.matsak.ellicity.lighting.payload.auth.AuthResponse;
import com.matsak.ellicity.lighting.payload.auth.LoginRequest;
import com.matsak.ellicity.lighting.payload.auth.SignUpManagerRequest;
import com.matsak.ellicity.lighting.payload.auth.SignUpRequest;
import com.matsak.ellicity.lighting.repository.UserRepository;
import com.matsak.ellicity.lighting.security.AuthProvider;
import com.matsak.ellicity.lighting.security.Authority;
import com.matsak.ellicity.lighting.security.TokenProvider;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        System.out.println(token);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return processRegistration(signUpRequest);
    }

    @PostMapping("/signup/manager")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerManager(@Valid @RequestBody SignUpManagerRequest signUpRequest) {
        return processRegistration(signUpRequest);
    }

    @NotNull
    private ResponseEntity<?> processRegistration(SignUpRequest signUpRequest) {
        User newUser = new User();
        String email = signUpRequest.getEmail();
        if(userRepository.existsByEmail(email)) {
            throw new BadRequestException("Email address already in use.");
        }

        User user = newUser;
        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }

    private User createNewUser(SignUpRequest signUpRequest, Authority role) {
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.LOCAL);
        user.setAuthority(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return user;
    }

}
