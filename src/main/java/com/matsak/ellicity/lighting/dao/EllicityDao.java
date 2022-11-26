package com.matsak.ellicity.lighting.dao;

import java.util.Optional;
import java.util.Set;

public interface EllicityDao<T> {
    Optional<T> getById(Long id);
    void save(T entity);
    boolean exists(Long id);
    abstract Set<T> getAll();

}
