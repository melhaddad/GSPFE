package ma.ilem.inventorymanagement.controller;

import ma.ilem.inventorymanagement.entity.Category;
import ma.ilem.inventorymanagement.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revisions;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SHOW_CATEGORIES', 'ALL_PRIVILEGES')")
    public ResponseEntity<List<Category>> findAll() {
        List<Category> items = categoryService.findAllByDeletedIsFalse();

        return ResponseEntity.ok().body(items);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('SHOW_CATEGORIES', 'ALL_PRIVILEGES')")
    public ResponseEntity<Category> findById(
            @PathVariable Long id
    ) {
        Category category = categoryService.findById(id);

        return ResponseEntity.ok().body(category);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADD_CATEGORY', 'ALL_PRIVILEGES')")
    public ResponseEntity<Object> save(
            @Valid @RequestBody Category category
    ) {
        Category savedCategory = categoryService.save(category);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCategory.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_CATEGORY', 'ALL_PRIVILEGES')")
    public ResponseEntity deleteById(
            @PathVariable Long id
    ) {
        categoryService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_CATEGORY', 'ALL_PRIVILEGES')")
    public ResponseEntity update(
            @Valid @RequestBody Category category,
            @PathVariable Long id
    ) {
        categoryService.update(category, id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("history")
    @PreAuthorize("hasAnyAuthority('SHOW_HISTORY', 'ALL_PRIVILEGES')")
    public ResponseEntity<List<Revisions<Integer, Category>>> history() {
        List<Revisions<Integer, Category>> result = categoryService.findAllHistory();

        return ResponseEntity.ok(result);
    }

}
