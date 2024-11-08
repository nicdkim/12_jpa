package com.ohgiraffers.section02.onetomany;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class OneToManyAssociationTests {

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

    @AfterAll
    public static void closeFactory() {
        emf.close();
    }

    @AfterEach
    public void closeManager() {
        em.close();
    }

    @Test
    void 일대다_연관관계_객체_조회_메소드() {
        int categoryCode = 10;

        CategoryAndMenu categoryAndMenu = em.find(CategoryAndMenu.class, categoryCode);

        Assertions.assertNotNull(categoryAndMenu);
        System.out.println("categoryCode : " + categoryCode);
    }

    @Test
    void 일대다_연관관계_객체_삽입_테스트() {
        CategoryAndMenu categoryAndMenu = new CategoryAndMenu();
        categoryAndMenu.setCategoryName("일대다 카테고리 추가 테스트");
        categoryAndMenu.setRefCategoryCode(null);

        Menu menu = new Menu();
        menu.setMenuName("방어회");
        menu.setMenuPrice(50000);
        menu.setOrderableStatus("N");
        menu.setCategoryCode(categoryAndMenu);

        categoryAndMenu.getMenuList().add(menu);

        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(categoryAndMenu);
        et.commit();

        CategoryAndMenu foundMenu = em.find(CategoryAndMenu.class, categoryAndMenu.getCategoryCode());
        System.out.println("foundMenu : " + foundMenu);
    }

}
