package com.matsak.ellicity.lighting.repository.systeminfo;

import com.matsak.ellicity.lighting.entity.sections.System;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemRepository extends JpaRepository<System, Long> {
    @Query("SELECT us FROM UserSystems us WHERE us.id.userId=:userId")
    List<System> findAllSystemsByUserId(Long userId);
    Optional<System> findByName(String name);

    @Query("SELECT sys FROM System sys" +
            " JOIN FETCH UserSystems us ON us.id.systemId=sys.id" +
            " WHERE us.id.systemId=:systemId AND us.id.userId=:userId")
    Optional<System> findByUserId(Long systemId, Long userId);
}
