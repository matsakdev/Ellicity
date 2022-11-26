package com.matsak.ellicity.lighting.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.Set;

public abstract class BaseDaoImpl<T, K> implements EllicityDao<T, K> {

    private static final Class<? extends T> T = ;
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<T> getById(K id) {
        return Optional.ofNullable(entityManager.find(T, id));
    }

    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public boolean exists(K id) {
        return entityManager.find(T, id) != null;
    }

    @Override
    public abstract Set<T> getAll();
}
