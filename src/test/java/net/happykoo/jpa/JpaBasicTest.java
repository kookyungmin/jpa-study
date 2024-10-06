package net.happykoo.jpa;

import net.happykoo.jpa.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = JpaApplication.class)
public class JpaBasicTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("Member Entity JPA Test")
    public void memberJpaTest() {
        executeTransaction((em) -> {
            String id = "happykoo";
            Member member = Member.builder()
                .id(id)
                .userName("해피쿠")
                .age(32)
                .build();

            //등록
            em.persist(member);

            //수정
            member.setAge(33);

            //한건 조회
            Member findMember = em.find(Member.class, id);
            assertEquals(id, findMember.getId());
            assertEquals(33, findMember.getAge());


            //목록 조회
            List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
            assertTrue(members.stream()
                    .anyMatch(m -> m.getId().equals(id)));

            //삭제
            em.remove(member);

            findMember = em.find(Member.class, id);
            assertNull(findMember);
        });
    }

    private void executeTransaction(Consumer<EntityManager> runEntityOperation) {
        EntityManagerFactory entityManagerFactory = applicationContext.getBean(EntityManagerFactory.class);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            runEntityOperation.accept(entityManager);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            entityManager.close();
        }
        entityManagerFactory.close();
    }
}
