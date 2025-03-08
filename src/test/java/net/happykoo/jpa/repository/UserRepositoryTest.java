package net.happykoo.jpa.repository;

import lombok.extern.slf4j.Slf4j;
import net.happykoo.jpa.domain.User;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;

@SpringBootTest
@Slf4j
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private List<User> userList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        userList.add(User.builder()
                .name("marco")
                .email("marco@planitsquare.com")
                .build());
        userList.add(User.builder()
                .name("dabin")
                .email("dabin@naver.com")
                .build());
        userList.add(User.builder()
                .name("happykoo")
                .email("rudals4549@naver.com")
                .build());
    }

    @Test
    @DisplayName("user CRUD Test")
    @Transactional
    @Rollback(value = true)
    public void userCrudTest() {
        userRepository.saveAll(userList);

        List<User> result1= userRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        assertEquals("marco", result1.get(0).getName());

        List<User> result2 = userRepository.findByNameIn(Lists.newArrayList("marco", "dabin"));
        assertEquals(2, result2.size());

        //page
        Page<User> result3 = userRepository.findAll(PageRequest.of(0, 2));
        assertEquals(userList.size(), result3.getTotalElements());
        assertEquals(2, result3.getNumberOfElements());

        //example matcher
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase("name")
                .withMatcher("email", endsWith());
        Example<User> example = Example.of(User.builder()
                .name("tt")
                .email("naver.com")
                .build(), exampleMatcher);
        List<User> result4 = userRepository.findAll(example);
        assertEquals(2, result4.size());

        //entity manager 가 lazy fetch 를 통해 값을 구할 때 쿼리 실행
//        User user1 = userRepository.getReferenceById(1L);
//        log.info("#### {}", user1);

        //entity manager 가 eager fetch find 쿼리 실행
        User userResult1 = userRepository.findByName("marco").orElse(null);
        assertEquals("marco", userResult1.getName());

        assertEquals(3, userRepository.count());

        userRepository.delete(userResult1);
        assertEquals(2, userRepository.count());

        //select 하고 하나씩 delete
//        userRepository.deleteAll();
        //delete query 한번 실행
        userRepository.deleteAllInBatch();
        assertEquals(0, userRepository.count());
    }
}