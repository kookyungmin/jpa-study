package net.happykoo;

import net.happykoo.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JpaTestCh3 {
    @Test
    @DisplayName("entity status test")
    public void test1() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("happykoo");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        //new
        Member member = Member.builder()
                .id("Happykoo")
                .userName("해피쿠")
                .age(32)
                .build();
        try {
            tx.begin();

            //managed
            em.persist(member);
            em.flush();

            assertEquals(true, em.contains(member));

            //detached
            em.detach(member);

            assertEquals(false, em.contains(member));

            //remove(detached 된 상태에서는 merge 해줘야함)
            em.remove(em.merge(member));
//            em.remove(member);

            //remove 되면, persistence context 에서 제거됨
            assertEquals(false, em.contains(member));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
        emf.close();
    }

    @Test
    @DisplayName("entity identity test")
    public void test2() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("happykoo");
        EntityManager em = emf.createEntityManager();
        //new
        Member member = Member.builder()
                .id("Happykoo")
                .userName("해피쿠")
                .age(32)
                .build();

        em.persist(member);

        Member entity1 = em.find(Member.class, member.getId());
        Member entity2 = em.find(Member.class, member.getId());

        assertEquals(entity1, entity2);
    }

    @Test
    @DisplayName("entity 수정 test")
    public void test3() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("happykoo");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        //new
        Member member = em.find(Member.class, "Happykoo2");

        if (Objects.isNull(member)) {
            member = Member.builder()
                    .id("Happykoo2")
                    .userName("해피쿠")
                    .age(32)
                    .build();

            em.persist(member);
        }

        member.setAge(50);
        member.setUserName("TTT");

        transaction.commit();

        assertEquals("TTT", em.find(Member.class, "Happykoo2").getUserName());
    }
}
