package com.matsak.ellicity.lighting.repository.systeminfo;

import com.matsak.ellicity.lighting.entity.sections.System;
import com.matsak.ellicity.lighting.entity.sections.UserSystems;
import com.matsak.ellicity.lighting.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestEntityManager
public class UserSystemsRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UserSystemsRepository userSystemsRepository;

    @Autowired
    SystemRepository systemRepository;

    @Test
    public void saveUserSystemsTest() {
        System system = new System("name", "passkey");
        assertEquals(testEntityManager.persist(system), systemRepository.save(system));

        UserSystems userSystems = new UserSystems(1L, system);
        assertEquals(testEntityManager.persist(userSystems), userSystemsRepository.save(userSystems));
    }

}