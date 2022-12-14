package com.matsak.ellicity.lighting.service;

import com.matsak.ellicity.lighting.dto.UserDto;
import com.matsak.ellicity.lighting.entity.user.User;
import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.exceptions.ResourceNotFoundException;
import com.matsak.ellicity.lighting.repository.systeminfo.SystemRepository;
import com.matsak.ellicity.lighting.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    SystemRepository systemRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void addSystem(User user, System system) {

    }

    @Override
    public List<System> getUserSystems(User user) {
        return null;
    }

    @Override
    public UserDto findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
//        List<System> systems = systemRepository.findAllSystemsByUserId(id);
//        user.ifPresent(usr -> usr.setUserSystems(systems));
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        return new UserDto(user.get().getId(), user.get().getEmail(), user.get().getName());
    }

    @Override
    public void updateUser(UserDto updatedUser) {
            User user = userRepository.findById(updatedUser.getId())
                    .orElseThrow(() -> new IllegalArgumentException("User must have id for updating"));
            user.setEmail(updatedUser.getEmail());
            user.setName(updatedUser.getName());
            userRepository.save(user);
    }
}
