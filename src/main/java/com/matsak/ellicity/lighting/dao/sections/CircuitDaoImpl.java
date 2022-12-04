package com.matsak.ellicity.lighting.dao.sections;

import com.matsak.ellicity.lighting.dao.BaseDaoImpl;
import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CircuitDaoImpl extends BaseDaoImpl<Circuit> implements CircuitDao {

    @PersistenceContext(name = "systemInfo")
    EntityManager entityManager;

    @Override
    public void saveBufferedCircuitData(Circuit sender, List<Measurement> measurements) {

    }

    @Override
    public Measurement getLastMeasurement(Circuit circuit) {
        return null;
    }

    @Override
    public Set<Circuit> getAll() {
        Query query = entityManager.createQuery("SELECT c FROM Circuit c");
        return (Set<Circuit>) query.getResultStream().collect(Collectors.toSet());
    }
}
