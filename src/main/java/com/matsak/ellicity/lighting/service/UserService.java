package com.matsak.ellicity.lighting.service;

import com.matsak.ellicity.lighting.entity.user.User;
import com.matsak.ellicity.lighting.entity.sections.System;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(User user);
    void addSystem(User user, System system);
    List<System> getUserSystems(User user);
    Optional<User> findUserById(Long id);

}
