package com.matsak.ellicity.lighting.repository;

import com.matsak.ellicity.lighting.dao.sections.CircuitDao;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

public class CircuitRepositoryImpl implements CircuitRepository {

    @Autowired
    CircuitDao circuitDAO;

    @Override
    public Optional<Circuit> getById(Long id) {
        circuitDAO.
    }

    @Override
    public void save(Circuit entity) {

    }

    @Override
    public Set<Circuit> getAll() {
        return null;
    }
}
