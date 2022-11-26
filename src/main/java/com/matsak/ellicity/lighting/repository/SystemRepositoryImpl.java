package com.matsak.ellicity.lighting.repository;

import com.matsak.ellicity.lighting.dao.SystemRepositoryDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

public class SystemRepositoryImpl implements SystemRepository {

    @Autowired
    SystemRepositoryDao systemRepositoryDao;


    @Override
    public void save(System entity) {

    }

    @Override
    public void remove(System entity) {

    }

    @Override
    public Set<System> getAll() {
        return null;
    }
}
