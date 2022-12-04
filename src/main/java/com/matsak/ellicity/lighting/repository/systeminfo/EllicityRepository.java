package com.matsak.ellicity.lighting.repository.systeminfo;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface EllicityRepository<T> {
    void save(T entity);
    void remove(T entity);
    Set<T> getAll();
}
