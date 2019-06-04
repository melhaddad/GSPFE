package ma.ilem.inventorymanagement.service.impl;

import ma.ilem.inventorymanagement.entity.Category;
import ma.ilem.inventorymanagement.exception.CategoryDuplicateNameException;
import ma.ilem.inventorymanagement.exception.CategoryHasItemsException;
import ma.ilem.inventorymanagement.exception.CategoryNotFoundException;
import ma.ilem.inventorymanagement.repository.CategoryRepository;
import ma.ilem.inventorymanagement.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Category findById(Long id) {
        Optional<Category> category = categoryRepository.findByIdAndDeletedIsFalse(id);

        if (!category.isPresent()) {
            throw new CategoryNotFoundException("La catégorie n'existe pas.");
        }

        return category.get();
    }

    @Override
    @Transactional
    public Category save(Category category) {
        Optional<Category> exist = categoryRepository.findByName(category.getName());

        if (exist.isPresent()) {
            throw new CategoryDuplicateNameException("Il existe déjà une catégorie avec ce nom.");
        }

        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Category> exist = categoryRepository.findByIdAndDeletedIsFalse(id);

        if (!exist.isPresent()) {
            throw new CategoryNotFoundException("La catégorie n'existe pas.");
        }

        Category category = exist.get();

        if (category.getItems().size() > 0) {
            throw new CategoryHasItemsException("Impossible de supprimer une catégorie qui a des matériels.");
        }

        category.setName("@" + category.getName() + " " + new Date().getTime());
        category.setDeleted(true);
    }

    @Override
    @Transactional
    public void update(Category category, Long id) {
        Optional<Category> categoryOptional = categoryRepository.findByIdAndDeletedIsFalse(id);

        if (!categoryOptional.isPresent()) {
            throw new CategoryNotFoundException("La catégorie n'existe pas.");
        }

        Optional<Category> sameName = categoryRepository.findByName(category.getName());

        if (sameName.isPresent() && !sameName.get().getId().equals(id)) {
            throw new CategoryDuplicateNameException("Il existe déjà une catégorie avec ce nom.");
        }

        Category exist = categoryOptional.get();

        exist.setName(category.getName());
        exist.setDescription(category.getDescription());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> findAllPageable(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public List<Category> findAllByDeletedIsFalse() {
        return categoryRepository.findAllByDeletedIsFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Revisions<Integer, Category>> findAllHistory() {
        List<Revisions<Integer, Category>> result = new ArrayList<>();

        List<Category> categories = categoryRepository.findAll();

        categories.forEach(category -> {
            Revisions<Integer, Category> revisions = categoryRepository.findRevisions(category.getId());

            result.add(revisions);
        });

        return result;
    }
}
