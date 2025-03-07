package net.happykoo.jpa.repository;

import lombok.extern.slf4j.Slf4j;
import net.happykoo.jpa.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Slf4j
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void userCrudTest() {
        userRepository.save(User.builder()
                .email("rudals4549@naver.com")
                .name("koo")
                .build());

        log.info("###### {}", userRepository.findAll());
    }

}