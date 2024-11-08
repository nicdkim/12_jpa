package com.ohgiraffers.section01.manytoone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class ManyToOneAssociationTest {

    /*
    * Association Mapping 은 Entity 클래스 간의 관계를 매핑하는 것을 의미한다.
    * 이를 통해 객체를 이용해 데이터베이스의 테이블 간의 관계를 매핑할 수 있다..
    *
    * 다중성에 의한 분류
    * 연관 관계가 있는 객체 관계에서는 실제로 연관을 가지는 객체의 수에 따라 분류된다.
    *
    * - N : 1 연관관계
    * - 1 : N 연관관계
    * - 1 : 1 연관관계
    *
    * Jpa 에서 연관관계를 잘못 설정하면 성능과 데이터 일관성에 큰 영향을 줄 수 있다.
    * 각 연관관계 유형에 따라 jpa 가 데이터베이스와 상호작용 하는 방식이 달라지기 떄문에
    * 잘못된 매핑 설정은 예상치 못한 쿼리를 발생 시키거나, 잘못된 접근을 유발할 수 있다.
    *
    * 테이블의 연관 관계는 외래 키를 이용하여 양방향 연관 관계의 특징을 가진다.
    * 참조에 의한 객체의 연관 관계는 단방향이다.
    * 객체간의 연관관계를 양방향으로 만들고 싶은 경우 반대 쪽에서도 필드를 추가해
    * 참조를 보관하면 된다.
    * 하지만 엄밀하게 이는 양방향 관계가 아니라 단방향 관계 2개로 볼 수 있다.
    * */

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

    /*
    * ManyToOne 은 다수의 엔티티가 하나의 엔티티를 참조하는 상황에서 사용된다.
    * 예를 들어 하나의 카테고리가 여러 개의 메뉴를 가질 수 있는 상황에서
    * 메뉴 엔티티가 카테고리를 참조하는 것이다.
    * 이 때 메뉴 엔티티가 Many, 카테고리 엔티티가 One 이 된다.
    * */
    @Test
    void 다대일_연관관계_조회_테스트() {
        int menuCode = 15;

        MenuAndCategoty foundMenu = em.find(MenuAndCategoty.class, menuCode);
        Category menuCategoty = foundMenu.getCategory();

        Assertions.assertNotNull(menuCategoty);
        System.out.println("menuCategory : " + menuCategoty);
    }

    @Test
    void 다대일_연관관계_객체지향쿼리_사용_조회_테스트() {
        String jpql = "SELECT c.categoryName FROM menu_and_category m JOIN " +
                "m.category c WHERE m.menuCode = 15";
        String category = em.createQuery(jpql, String.class).getSingleResult();

        Assertions.assertNotNull(category);
        System.out.println("category : " + category);
    }

    @Test
    void 다대일_연관관계_객체_삽입_테스트() {
        MenuAndCategoty menuAndCategoty = new MenuAndCategoty();
        menuAndCategoty.setMenuCode(99999);
        menuAndCategoty.setMenuName("양미리구이");
        menuAndCategoty.setMenuPrice(10000);

        Category category = new Category();
        category.setCategoryCode(33333);
        category.setCategoryName("신규카테고리");
        category.setRefCategoryCode(null);

        menuAndCategoty.setCategory(category);
        menuAndCategoty.setOrderableStatus("Y");

        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(menuAndCategoty);
        et.commit();

        MenuAndCategoty foundMenuAndCategory = em.find(MenuAndCategoty.class, 99999);
        Assertions.assertEquals(99999, foundMenuAndCategory.getMenuCode());
        Assertions.assertEquals(33333, foundMenuAndCategory.getCategory().getCategoryCode());

    }

    @Test
    void 영속성_삭제_테스트() {
        MenuAndCategoty menuAndCategoty = em.find(MenuAndCategoty.class, 99999);

        EntityTransaction et = em.getTransaction();
        et.begin();
        em.remove(menuAndCategoty);
        et.commit();
        MenuAndCategoty deleteMenu = em.find(MenuAndCategoty.class, 99999);
        Category foundCategory = em.find(Category.class, 33333);
        System.out.println("foundCategory : " + foundCategory);
        System.out.println("deleteMenu : " + deleteMenu);
    }

    @Test
    void Merger_Insert_테스트() {
        MenuAndCategoty menuAndCategoty = new MenuAndCategoty();
        menuAndCategoty.setMenuPrice(15000);
        menuAndCategoty.setMenuName("merger insert 메뉴");
        menuAndCategoty.setOrderableStatus("Y");
        Category category = new Category();
        category.setCategoryName("merger카테고리");

        menuAndCategoty.setCategory(category);

        EntityTransaction et = em.getTransaction();
        et.begin();
        MenuAndCategoty mergeMenu = em.merge(menuAndCategoty);

        et.commit();

        System.out.println("mergeMenu : " + mergeMenu);

    }

    @Test
    void detach_테스트() {
        /*
        * Detach 의 경우 해당 엔티티를 영속성 컨텍스트에서 관리하지 않겠다고 하는 것이다.
        * (준영속화)
        * 그러나 해당 관계를 맺고 있는 엔티티의 수정이 생기는 경우 해당 엔티티는 관리 중이기
        * 때문에 함께 관계를 가지고 간다.
        * 이러한 문제를 해결하기 위해 CascadeType을 DETACH 로 설정하면
        * 관계 요소도 함께 영속성에서 관리하지 않겠다는 것이다.
        * */

        MenuAndCategoty menuAndCategoty = em.find(MenuAndCategoty.class, 99999);
        menuAndCategoty.setMenuName("변경함");
        Category category = menuAndCategoty.getCategory();
        category.setCategoryName("안바뀜 ?");
        menuAndCategoty.setCategory(category);

        EntityTransaction et = em.getTransaction();
        et.begin();
        em.detach(menuAndCategoty);
        et.commit();

        Category category1 = em.find(Category.class, 33333);
        System.out.println("category1 : " + category1);
    }

}
