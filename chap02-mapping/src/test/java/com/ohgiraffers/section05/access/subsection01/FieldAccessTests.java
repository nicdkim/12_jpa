package com.ohgiraffers.section05.access.subsection01;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class FieldAccessTests {

    private static EntityManager em;

    private static EntityManagerFactory emf;

    @BeforeAll
    public static void initFactory() {
        emf = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        em = emf.createEntityManager();
    }

    @AfterEach
    public void closeManager() {
        em.close();
    }

    @AfterAll
    public static void closeFactory() {
        emf.close();
    }

    @Test
    void 필드_접근_테스트() {
        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("user01");
        member.setMemberId("pass02");

        em.persist(member);

        Member foundMember = em.find(Member.class, 1);
        Assertions.assertEquals(member, foundMember);
        System.out.println(foundMember);
    }

}
