package com.matsak.ellicity.lighting.service;

import com.matsak.ellicity.lighting.dto.UserDto;
import com.matsak.ellicity.lighting.entity.user.User;
import com.matsak.ellicity.lighting.entity.sections.System;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    void addSystem(User user, System system);
    List<System> getUserSystems(User user);
    UserDto findUserById(Long id);

    void updateUser(UserDto updatedUser);

    List<UserDto> findAllUsers();
}
