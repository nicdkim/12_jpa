package com.ohgiraffers.chap05springdata.category.service;

import com.ohgiraffers.chap05springdata.category.entity.Category;
import com.ohgiraffers.chap05springdata.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> selectAllCategory() {

        List<Category> categoryList = categoryRepository.findAll();
        if(categoryList.isEmpty()) {
            return null;
        }
        return categoryList;
    }

    public Category selectCategoryByCode(int categoryCode) {
        Category category = categoryRepository.findByCategoryCode(categoryCode);

        if(Objects.isNull(category)) {
            return null;
        }
        return category;
    }

    public Category insertCategory(String categoryName) {

        // 이름 중복 체크
        Category foundCategory = categoryRepository.findByCategoryName(categoryName);

        if(!Objects.isNull(foundCategory)) {
            return null;
        }

        Category insertCategory = new Category();
        insertCategory.setCategoryName(categoryName);

        Category result = categoryRepository.save(insertCategory);
        /*
        * save() 는 jpa 에서 EntityManager 를 통해 데이터를 저장하는 메소드
        * 기본적으로, jpa 는 트랜잭션 내에서 자동으로 커밋을 처리한다.
        * 새로운 엔티티의 경우 : db에 저장하고 저장한 객체 반환
        * 기존에 존재하는 엔티티의 경우 : 해당 엔티티의 정보를 업데이트하고 업데이트한 객체 반환
        * */
        return result;
    }


    public Category updateCategory(String categoryName, Integer categoryCode) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryCode);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setCategoryName(categoryName);
            return categoryRepository.save(category);
        }

        return null;
    }

}
