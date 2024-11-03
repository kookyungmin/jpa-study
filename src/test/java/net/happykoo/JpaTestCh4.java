package net.happykoo;

import net.happykoo.entity.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JpaTestCh4 {
    @Test
    @DisplayName("id strategy IDENTITY")
    public void test1() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("happykoo");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        Board board = new Board();
        em.persist(board);
        tx.commit();
        assertNotNull(board.getId());
    }
}
