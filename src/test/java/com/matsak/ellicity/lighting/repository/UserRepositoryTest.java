package com.matsak.ellicity.lighting.repository;

import com.matsak.ellicity.lighting.entity.user.User;
import com.matsak.ellicity.lighting.repository.user.UserRepository;
import com.matsak.ellicity.lighting.security.AuthProvider;
import com.matsak.ellicity.lighting.security.Authority;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestEntityManager
public class UserRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    UserRepository userRepository;

    @Test
    public void savingUserTest() {
        User user = new User();
        user.setEmail("email@gmail.com");
        user.setName("name");
        user.setEmailVerified(true);
        user.setPassword("1234");
        user.setProvider(AuthProvider.LOCAL);
        user.setImageUrl("images/img3.png");
        user.setAuthority(Authority.ROLE_USER);

        user = testEntityManager.persist(user);

        assertEquals(user, userRepository.findById(user.getId()).get());

    }


}
