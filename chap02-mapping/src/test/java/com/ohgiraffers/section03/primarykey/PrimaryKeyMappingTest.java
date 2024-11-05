package com.ohgiraffers.section03.primarykey;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

public class PrimaryKeyMappingTest {

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
    * Primary key 에는 @Id 어노테이션과 @GeneratedValue 어노테이션을 사용한다.
    * @Id 어노테이션은 엔티티 클래스에서 Primary key 역할을 하는 필드를 지정할 때 사용한다.
    * 데이터 베이스마다 기본 키를 생성하는 방식이 서로 다르다.
    * @GeneratedValue 는 가음과 같은 속성을 가지고 있다.
    * 
    * - strategy : 자동 생성 전략을 지정
    * - GenerationType.IDENTITY : 기본 키 생성을 데이터베이스에 위임(Mysql 의 AutoIncrement)
    * - GenerationType,SEQUENCE : 데이터베이스 시퀀스 객체 사용 (ORACLE 의 SEQUENCE)
    * - GenerationType.TABLE : 키 생성 테이블 사용
    * - GenerationType.AUTO : 자동 선택
    * */

    @Test
    public void 식별자_매핑_테스트() {
        Member member = new Member();
        member.setMemberId("user01");
        member.setMemberPwd("pass01");
        member.setNickname("홍길동");
        member.setPhone("010-1234-5678");

        Member member2 = new Member();
        member2.setMemberId("user02");
        member2.setMemberPwd("pass02");
        member2.setNickname("유관순");
        member2.setPhone("010-1111-1111");

        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(member);
        em.persist(member2);
        et.commit();

        String jpql = "SELECT A.memberNo FROM member_section03 A";
        List<Integer> memberNoList = em.createQuery(jpql, Integer.class).getResultList();
        System.out.println(memberNoList);
    }

}
