package com.ohgiraffers.section02.column;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class ColumnMappingTest {

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
    public void 컬럼에서_사용하는_속성() {
        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("user01");
        member.setMemberPwd("pass01");
        member.setNickname("홍길동");
        member.setPhone("010-1234-5678");
        member.setAddress("서울시 서초구");
        member.setEmail("hong@gmail.com");

        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(member);
        et.commit();

        Member foundManager = em.find(Member.class, member.getMemberNo());
        Assertions.assertEquals(member.getMemberNo(), foundManager.getMemberNo());

    }
}
