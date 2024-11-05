package com.ohgiraffers.section04.enumtype;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class EnumTypeMappingTest {

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
    void enum타입_매핑_테스트() {
        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("user01");
        member.setMemberPwd("pass01");
        member.setNickname("홍길동");
        member.setMemberRole(RoleType.ADMIN);

        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(member);
        et.commit();

        Member foundMember = em.find(Member.class, member.getMemberNo());
        Assertions.assertEquals(member.getMemberNo(), foundMember.getMemberNo());
    }

}
