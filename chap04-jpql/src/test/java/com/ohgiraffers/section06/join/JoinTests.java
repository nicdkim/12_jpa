package com.ohgiraffers.section06.join;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

public class JoinTests {

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

    @Test
    void 내부조인을_이용한_조회_테스트() {
        String jpql = "SELECT m FROM menu_section06 m JOIN m.category c";
        List<Menu> menuList = em.createQuery(jpql, Menu.class).getResultList();

        Assertions.assertNotNull(menuList);
        for(Menu menu : menuList) {
            System.out.println(menu);
        }
    }

    @Test
    void RIGHT_JOIN_테스트() {
        String jpql = "SELECT m.menuName, c.categoryName FROM menu_section06 m " +
                " RIGHT JOIN m.category c ORDER BY m.category.categoryCode";

        List<Object[]> menuList = em.createQuery(jpql, Object[].class).getResultList();

        Assertions.assertNotNull(menuList);
        for(Object[] obj : menuList) {
            for(Object obj2 : obj) {
                System.out.println(obj2);
            }
        }
    }

    @Test
    void 페치조인을_이용한_조회_테스트() {
        /*
        * 페치 조인을 하면 처음 SQL 실행 후 로딩할 때 조인 결과를 다 조회한 뒤에 사용하는 방식
        * 그래서 쿼리 실행 횟수가 줄어든다.
        * 대부분의 경우 성능이 향상된다.
        * */

        String jpql = "SELECT m FROM menu_section06 m JOIN FETCH m.category c";
        List<Menu> menuList = em.createQuery(jpql, Menu.class).getResultList();

        Assertions.assertNotNull(menuList);
        for(Menu menu : menuList) {
            System.out.println(menu);
        }
    }

}
