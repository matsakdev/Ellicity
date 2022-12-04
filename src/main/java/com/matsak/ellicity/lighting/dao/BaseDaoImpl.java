package com.matsak.ellicity.lighting.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.Set;

public abstract class BaseDaoImpl<T> implements EllicityDao<T> {

    private final Class<? extends T> T = null;
    @PersistenceContext
    EntityManager entityManager;

//    @Override
//    public Optional<T> getById(K id) {
//        return Optional.ofNullable(entityManager.find(T, id));
//    }

    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

//    @Override
//    public boolean exists(K id) {
//        return entityManager.find(T, id) != null;
//    }

    @Override
    public boolean exists(Long id) {
        return true;
    }

    @Override
    public Optional<T> getById(Long id) {
        return Optional.ofNullable(null);
    }

    @Override
    public abstract Set<T> getAll();
}
