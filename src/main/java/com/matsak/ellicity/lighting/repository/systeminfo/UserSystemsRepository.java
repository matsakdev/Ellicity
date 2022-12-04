package com.matsak.ellicity.lighting.repository.systeminfo;

import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.entity.sections.UserSystems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSystemsRepository extends JpaRepository<UserSystems, UserSystems.Id> {

    @Query("SELECT us FROM UserSystems us WHERE us.id.userId=:userId")
    List<System> findByUserId(Long userId);
}
