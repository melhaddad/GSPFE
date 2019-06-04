package ma.ilem.inventorymanagement.service.impl;

import ma.ilem.inventorymanagement.entity.Item;
import ma.ilem.inventorymanagement.exception.ItemHasAssignmentException;
import ma.ilem.inventorymanagement.exception.ItemNotFoundException;
import ma.ilem.inventorymanagement.repository.ItemRepository;
import ma.ilem.inventorymanagement.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Item> findAllPageable(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    @Transactional(readOnly = true)
    public Item findById(Long id) {
        Optional<Item> item = itemRepository.findByIdAndDeletedIsFalse(id);

        if (!item.isPresent()) {
            throw new ItemNotFoundException("Le matériel n'existe pas.");
        }

        return item.get();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Item> exist = itemRepository.findByIdAndDeletedIsFalse(id);

        if (!exist.isPresent()) {
            throw new ItemNotFoundException("Le matériel n'existe pas.");
        }

        Item item = exist.get();

        if (item.getAssignment().size() > 0) {
            throw new ItemHasAssignmentException("Impossible de supprimer un matériel qui a des affectations.");
        }

        item.setDeleted(true);
    }

    @Override
    @Transactional
    public void update(Item item, Long id) {
        Optional<Item> exist = itemRepository.findByIdAndDeletedIsFalse(id);

        if (!exist.isPresent()) {
            throw new ItemNotFoundException("Le matériel n'existe pas.");
        }

        Item old = exist.get();

        old.setName(item.getName());
        old.setDescription(item.getDescription());
        old.setInfo(item.getInfo());
        old.setWarning(item.getWarning());
        old.setError(item.getError());
        old.setCategory(item.getCategory());
    }


    @Override
    @Transactional
    public void supply(Long quantity, Long id) {
        Optional<Item> itemOptional = itemRepository.findByIdAndDeletedIsFalse(id);

        if (!itemOptional.isPresent()) {
            throw new ItemNotFoundException("Le matériel n'existe pas.");
        }

        Item old = itemOptional.get();

        old.setQuantity(old.getQuantity() + quantity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findAllByDeletedIsFalse() {
        return itemRepository.findAllByDeletedIsFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Revisions<Integer, Item>> findAllHistory() {
        List<Revisions<Integer, Item>> result = new ArrayList<>();

        List<Item> items = itemRepository.findAll();

        items.forEach(item -> {
            Revisions<Integer, Item> revisions = itemRepository.findRevisions(item.getId());

            result.add(revisions);
        });

        return result;
    }
}
