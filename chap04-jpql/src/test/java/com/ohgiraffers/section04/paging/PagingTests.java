package com.ohgiraffers.section04.paging;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

public class PagingTests {

    private static EntityManagerFactory emf;

    private EntityManager em;

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
    * 페이징 처리용 SQL 문법은 각각 DBMS 마다 다르다는 문제점이 있다.
    * JPA 는 이러한 페이징을 API 를 통해 추상화해서 간단하게 처리할 수 있도록 제공한다.
    * 
    * - 페이징 (데이터베이스에서 많은 양의 데이터를 효과적으로 조회하기 위한 기술)
    *  : 데이터의 일부만을 한 번에 조회하여 성능을 개성하고,
    * 사용자 인터페이스 (UI) 에서 데이터를 보다 쉽게 표현할 수 있다.
    * 일반적으로 대량의 데이터를 한 번에 조회하는 경우 성능 저하 및 메모리 소모가 발생해
    * 페이징을 통해 필요한 데이터만을 가져오는 것이 유리하다.
    * */

    @Test
    void 페이징_api를_이용한_조회_테스트() {

        int offset = 10; // 10번째 행부터 조회하겠다.
        int limit = 5; // 5개의 행을 가져오겠다.

        String jpql = "SELECT m FROM menu_section04 m ORDER BY m.menuCode DESC";
        List<Menu> menuList = em.createQuery(jpql, Menu.class)
                .setFirstResult(offset) // 시작위치
                .setMaxResults(limit)   // 조회할 갯수
                .getResultList();

        Assertions.assertNotNull(menuList);
        for (Menu menu : menuList) {
            System.out.println(menu);
        }

    }

}
