package com.matsak.ellicity.lighting.repository.systeminfo;

import com.matsak.ellicity.lighting.entity.sections.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    @Query("SELECT dev FROM UserSystems us" +
            " JOIN FETCH System sys ON sys.id = us.id.systemId" +
            " JOIN FETCH Circuit circ ON circ.system.id = sys.id" +
            " JOIN FETCH Device dev ON dev.circuit.id = circ.id" +
            " WHERE us.id.userId = :userId AND dev.id = :deviceId")
    Optional<Device> getUserDeviceById(Long userId, Long deviceId);
}
