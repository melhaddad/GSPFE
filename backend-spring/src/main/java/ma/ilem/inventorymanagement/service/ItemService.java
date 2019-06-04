package ma.ilem.inventorymanagement.service;

import ma.ilem.inventorymanagement.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revisions;

import java.util.List;

public interface ItemService {

    Page<Item> findAllPageable(Pageable pageable);

    Item save(Item item);

    Item findById(Long id);

    void deleteById(Long id);

    void update(Item item, Long id);

    void supply(Long quantity, Long id);

    List<Item> findAll();

    List<Item> findAllByDeletedIsFalse();

    List<Revisions<Integer, Item>> findAllHistory();
}
