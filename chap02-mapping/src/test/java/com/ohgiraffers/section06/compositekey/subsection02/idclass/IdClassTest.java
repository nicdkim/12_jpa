package com.ohgiraffers.section06.compositekey.subsection02.idclass;

import com.ohgiraffers.section06.compositekey.subsection02.idclass.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class IdClassTest {

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

    /*
    * jpa 에서 복합키를 매핑하는 방법에는 두 가지가 있다.
    * 1. @EmbeddedId 어노테이션
    * - 이 방법은 복합 키를 구성하는 필드들을 하나씩 클래스로 묶은 뒤 해당 클래스를
    * 어노테이션을 사용하여 매핑하는 방법이다.
    * 복합키의 일부 필드만을 매핑할 수도 있기 때문에, 필드 수가 많은 경우에는 유연한 매핑이 가능하다는 장점이 있다.
    *
    * 2. @IdClass 어노테이션
    * - 이 방법은 복합 키를 구성하는 필드들을 별도의 클래스로 분리한 뒤, 해당 클래스를 @IdClass 어노테이션의
    * 값으로 지정해주는 것이다. 이 방법은 별도의 매핑 클래스를 사용하지 않기 때문에 코드가 간결하다.
    *
    * 복합 키의 매핑에서는 복합 키 클래스의 Equals 와 hashCode 메소드를 구현해야 한다는 점에 주의한다.
    * 이는 jpa 에서 엔티티 객체의 동일성을 판단하기 위해 필요하다.
    * */

    @Test
    public void 임베디드_아이디_사용한_복합키_매핑_테스트() {
        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("user01");
        member.setPhone("010-1111-1111");
        member.setAddress("서울시 서초구");

        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(member);
        et.commit();

        Member foundMember = em.find(Member.class, new MemberPK(1, "user01"));
        Assertions.assertEquals(member, foundMember);
    }


}
