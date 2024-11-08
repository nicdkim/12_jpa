package com.ohgiraffers.chap05springdata.category.repository;

import com.ohgiraffers.chap05springdata.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {


    Category findByCategoryCode(int categoryCode);

    Category findByCategoryName(String categoryName);
}
