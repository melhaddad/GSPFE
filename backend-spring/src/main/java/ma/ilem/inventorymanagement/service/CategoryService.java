package ma.ilem.inventorymanagement.service;

import ma.ilem.inventorymanagement.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revisions;

import java.util.List;

public interface CategoryService {

    Category findById(Long id);

    Category save(Category category);

    void deleteById(Long id);

    void update(Category category, Long id);

    Page<Category> findAllPageable(Pageable pageable);

    List<Category> findAll();

    List<Category> findAllByDeletedIsFalse();

    List<Revisions<Integer, Category>> findAllHistory();
}
