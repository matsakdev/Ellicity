package com.matsak.ellicity.lighting.controller;

import com.matsak.ellicity.lighting.entity.user.User;
import com.matsak.ellicity.lighting.exceptions.ResourceNotFoundException;
import com.matsak.ellicity.lighting.repository.user.UserRepository;
import com.matsak.ellicity.lighting.security.CurrentUser;
import com.matsak.ellicity.lighting.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}