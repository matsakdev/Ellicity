package com.matsak.ellicity.lighting.service;

import com.matsak.ellicity.lighting.entity.User;
import com.matsak.ellicity.lighting.entity.sections.System;

import java.util.List;

public interface UserService {
    void saveUser(String userName, String password);
    void addSystem(User user, System system);
    List<System> getUserSystems(User user);

}
