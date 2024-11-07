package com.ohgiraffers.section02.parameter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Scanner;

public class ParameterBindingTest {

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
    * 파라미터를 바인딩 하는 방법
    * 1. 이름 기준 파라미터 ':' 다음에 이름 기준 파라미터를 지정한다.
    * 2. 위치 기준 파라미터 '?' 다음에 값을 주고 위치 값은 1부터 시작한다.
    * */

    @Test
    void 이름_기준_파라미터_바인딩_메뉴_조회_테스트() {
        String menuNameParameter = "한우딸기국밥";
        String jpql = "SELECT m FROM menu_section02 m WHERE m.menuName = :menuName";
        List<Menu> menuList = em.createQuery(jpql, Menu.class)
                .setParameter("menuName", menuNameParameter)
                .getResultList();
        Assertions.assertNotNull(menuList);
        for(Menu menu : menuList) {
            System.out.println(menu);
        }

    }

    @Test
    void 위치_기준_파라미터_바인딩_메뉴_목록_조회_테스트() {
        String menuNameParameter = "한우딸기국밥";
        String jpql = "SELECT m FROM menu_section02 m WHERE m.menuName = ?1";
        List<Menu> menuList = em.createQuery(jpql, Menu.class)
                .setParameter(1, menuNameParameter)
                .getResultList();
        Assertions.assertNotNull(menuList);
        for(Menu menu : menuList) {
            System.out.println(menu);
        }
    }

    // 메뉴 이름 입력 시 입력한 값이 포함된 메뉴 조회
    @Test
    void 메뉴_이름_입력시_입력한_값이_포함된_메뉴_조회() {
        Scanner sc = new Scanner(System.in);
        System.out.print("검색할 메뉴 이름을 입력하세요:");
        String inputName = "마늘";

        String jpql = "SELECT m FROM menu_section01 AS m WHERE m.menuName LIKE :inputName";
        TypedQuery<Menu> query = em.createQuery(jpql, Menu.class);
        query.setParameter("inputName", "%" + inputName + "%");
        List<Menu> menuList = query.getResultList();
        Assertions.assertNotNull(menuList);

        if (menuList.isEmpty()) {
            System.out.println("조회된 메뉴가 없습니다.");
        } else {
            for (Menu menu : menuList) {
                System.out.println(menu);
            }
        }
    }

    



}
