package com.matsak.ellicity.lighting.repository;

import java.util.Optional;
import java.util.Set;

public interface EllicityRepository<T> {
    void save(T entity);
    void remove(T entity);
    Set<T> getAll();
}
