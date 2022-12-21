package com.matsak.ellicity.lighting.controller;

import com.matsak.ellicity.lighting.dto.UserDto;
import com.matsak.ellicity.lighting.payload.ApiResponse;
import com.matsak.ellicity.lighting.security.Authority;
import com.matsak.ellicity.lighting.security.CurrentUser;
import com.matsak.ellicity.lighting.security.UserPrincipal;
import com.matsak.ellicity.lighting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public UserDto getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.findUserById(userPrincipal.getId());
    }

    @GetMapping
    @PreAuthorize("hasRole('MANAGER')")
    public List<UserDto> getAllUsers(@CurrentUser UserPrincipal userPrincipal) {
        return userService.findAllUsers();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse updateUser(@PathVariable(name = "id") Long userId,
                                  @RequestBody UserDto updatedUser,
                                  @AuthenticationPrincipal UserPrincipal principal) {
        if (!userId.equals(principal.getId()) && !principal.getAuthorities().contains(Authority.ROLE_MANAGER)) {
            return new ApiResponse(false, "You cannot update another user");
        }
            userService.updateUser(updatedUser);
        return new ApiResponse(true, "You've successfully updated info");
    }

}
