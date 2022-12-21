package com.matsak.ellicity.lighting.repository.systeminfo;

import com.matsak.ellicity.lighting.entity.sections.Circuit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CircuitRepository extends JpaRepository<Circuit, Long> {

    @Query("SELECT circ FROM Circuit circ" +
            " JOIN FETCH System sys ON circ.system = sys" +
            " JOIN FETCH UserSystems us ON us.id.systemId = sys.id" +
            " WHERE us.id.userId=:userId AND sys.id=:systemId")
    List<Circuit> findAllCircuitsByUserIdAndSystemId(Long userId, Long systemId);
}
