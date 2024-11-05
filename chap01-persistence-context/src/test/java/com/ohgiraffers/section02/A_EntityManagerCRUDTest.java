package com.ohgiraffers.section02;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class A_EntityManagerCRUDTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeAll
    public static void initFactory() {
        emf = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        em = emf.createEntityManager();
    }

    @AfterAll
    public static void closeManager() {
        em.close();
    }

    @AfterEach
    public void closeEntityManager() {
        em.close();
    }

    @Test
    public void 메뉴코드로_메뉴조회_테스트() {
        int menuCode = 1;

        Menu foundMenu = em.find(Menu.class, menuCode);
        System.out.println("foundMenu = " + foundMenu);
        Assertions.assertNotNull(foundMenu);
    }

    @Test
    public void 새로운_메뉴_추가_테스트() {
        Menu menu = new Menu();
        menu.setMenuName("jpa 테스트 메뉴");
        menu.setMenuPrice(5000);
        menu.setCategoryCode(4);
        menu.setOrderableStatus("Y");

        // 데이터베이스의 상태 변화를 하나의 단위로 묶어주는 기능을 할 객체
        EntityTransaction et = em.getTransaction();

        et.begin(); // 트랜잭션 활성화

        try {
            em.persist(menu);   // 메모리 단계의 저장
            et.commit();    // db에 명령을 넣음
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        // 데이터가 영속성 컨텍스트에 포함되어 있는지 확인
        Assertions.assertTrue(em.contains(menu));
    }

    @Test
    public void 메뉴_수정하기_테스트() {
        Menu menu = em.find(Menu.class, 2);
        System.out.println("menu : " + menu);
        EntityTransaction et = em.getTransaction();
        et.begin();

        try {
            menu.setMenuName("쿠우쿠우골드");
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        Assertions.assertEquals("쿠우쿠우골드",
                em.find(Menu.class,2).getMenuName());
    }
    
    @Test
    public void 메뉴_삭제하기_테스트() {
        Menu menuToRemove = em.find(Menu.class, 37);
        EntityTransaction et = em.getTransaction();
        et.begin();

        try {
            em.remove(menuToRemove);
            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        Menu removeMenu = em.find(Menu.class, 37);
        Assertions.assertNull(removeMenu);
    }

}
