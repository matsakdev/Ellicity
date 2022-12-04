package com.matsak.ellicity.lighting.service.websocket;

import com.matsak.ellicity.lighting.entity.user.User;
import com.matsak.ellicity.lighting.dto.Measurement;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import com.matsak.ellicity.lighting.payload.websocket.response.SendCurrentMeasurement;
import com.matsak.ellicity.lighting.repository.systeminfo.CircuitRepository;
import com.matsak.ellicity.lighting.repository.user.UserRepository;
import com.matsak.ellicity.lighting.service.sections.CircuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class SendUserMeasurementImpl implements SendUserMeasurement {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CircuitRepository circuitRepository;

    @Autowired
    CircuitService circuitService;

    @Override
    public SendCurrentMeasurement sendCurrentMeasurementByEmail(String userEmail, Long circuitId) {
        User user = getUserByEmail(userEmail);
        Circuit circuit = getCircuitById(circuitId);
        return getFormattedMeasurement(circuit);
    }

    private User getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User @email" + userEmail + " doesn't exist"));
    }

    private Circuit getCircuitById(Long circuitId) {
        return circuitRepository.findById(circuitId)
                .orElseThrow(() -> new IllegalArgumentException("Circuit @id" + circuitId + " doesn't exist"));
    }

//    private void verifyUserAccess(User user, Circuit circuit) {
//        if (circuit.getSystem()
//                .getUsers()
//                .stream()
//                .noneMatch(circuitUser -> circuitUser.equals(user))) throw
//                new AccessDeniedException("User @email " + user.getEmail() + "doesn't " +
//                        "have access to the circuit @id: " + circuit.getId());
//    }

    private SendCurrentMeasurement getFormattedMeasurement(Circuit circuit) {
        Measurement measurement = getMeasurement(circuit);
        return new SendCurrentMeasurement(circuit.getSystem(), circuit, measurement);
    }

    private Measurement getMeasurement(Circuit circuit) {
        return circuitService.getLastMeasurement(circuit);
    }
}
