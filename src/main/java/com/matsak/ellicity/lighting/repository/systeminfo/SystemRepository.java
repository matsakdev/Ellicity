package com.matsak.ellicity.lighting.repository.systeminfo;

import com.matsak.ellicity.lighting.entity.sections.System;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemRepository extends JpaRepository<System, Long> {
    @Query("SELECT us FROM UserSystems us WHERE us.id.userId=:id")
    List<System> findByUserId(Long id);
    Optional<System> findByName(String name);
}
