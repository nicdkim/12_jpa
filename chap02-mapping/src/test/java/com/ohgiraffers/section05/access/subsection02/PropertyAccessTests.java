package com.ohgiraffers.section05.access.subsection02;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class PropertyAccessTests {

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
    void 프로퍼티_접근_테스트() {
        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("user01");
        member.setMemberPwd("pass01");
        member.setNickname("홍길동");

        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(member);
        et.commit();

        String jpql = "SELECT memberId FROM member_section05_subsection02 WHERE memberNo = 1";
        String registNickName = em.createQuery(jpql, String.class).getSingleResult();
        System.out.println(registNickName);
        Assertions.assertEquals("user01", registNickName);
    }

}
