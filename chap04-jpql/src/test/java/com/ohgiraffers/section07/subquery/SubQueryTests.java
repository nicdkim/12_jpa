package com.ohgiraffers.section07.subquery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

public class SubQueryTests {

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
    * JPQL 도 SQL 처럼 서브 쿼리를 지원한다.
    * 하지만 select, from 절에서는 사용할 수 없고, where, having 절에서만 사용이 가능하다.
    * */

    @Test
    void 서브쿼리를_이용한_메뉴_조회_테스트() {
        String categoryNameParameter = "한식";
        String jpql = "SELECT m FROM menu_section07 m WHERE m.categoryCode = "
                + "(SELECT c.categoryCode FROM category_section07 c WHERE c.categoryName = : categoryName)";
        List<Menu> menuList = em.createQuery(jpql, Menu.class)
                .setParameter("categoryName", categoryNameParameter)
                .getResultList();
        Assertions.assertNotNull(menuList);
        for (Menu menu : menuList) {
            System.out.println(menu);
        }
    }

}
