package com.ohgiraffers.section01.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class EntityMappingTest {

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
    void 테스트_만들기_테스트() {
        Member member = new Member();

        member.setMemberNo(1);
        member.setMemberId("user01");
        member.setMemberPwd("pass01");
        member.setNickname("홍길동");
        member.setPhone("010-1111-1111");

        em.persist(member);

        Member foundMember = em.find(Member.class, member.getMemberNo());
        Assertions.assertEquals(member.getMemberNo(), foundMember.getMemberNo());
    }

    /*
    * Commit 하지 않았기 때문에 dml 은 db에 등록되지 않았지만, ddl 구문은
    * autoCommit 이기 때문에 테이블은 생성되어 있다.
    * 생성되는 컬럼의 순서는 pk가 우선이며,
    * 일반 컬럼은 유니코드 오름차순으로 생성된다.
    * */
}
