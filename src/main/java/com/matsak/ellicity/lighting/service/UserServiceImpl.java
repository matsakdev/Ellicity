package com.matsak.ellicity.lighting.service;

import com.matsak.ellicity.lighting.entity.user.User;
import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.repository.systeminfo.SystemRepository;
import com.matsak.ellicity.lighting.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public Optional<User> findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        List<System> systems = systemRepository.findByUserId(id);
        user.ifPresent(usr -> usr.setUserSystems(systems));
        return user;
    }
}
