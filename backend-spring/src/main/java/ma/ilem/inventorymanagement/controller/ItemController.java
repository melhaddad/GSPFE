package ma.ilem.inventorymanagement.controller;

import ma.ilem.inventorymanagement.entity.Item;
import ma.ilem.inventorymanagement.pojo.Supply;
import ma.ilem.inventorymanagement.service.ItemService;
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
@RequestMapping("api/items")
public class    ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SHOW_ITEMS', 'ALL_PRIVILEGES')")
    public ResponseEntity<List<Item>> findAll() {
        List<Item> items = itemService.findAllByDeletedIsFalse();

        return ResponseEntity.ok().body(items);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('SHOW_ITEMS', 'ALL_PRIVILEGES')")
    public ResponseEntity<Item> findById(
            @PathVariable Long id
    ) {

        Item item = itemService.findById(id);

        return ResponseEntity.ok().body(item);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADD_ITEM', 'ALL_PRIVILEGES')")
    public ResponseEntity<Object> save(
            @Valid @RequestBody Item item
    ) {
        Item savedItem = itemService.save(item);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedItem.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_ITEM', 'ALL_PRIVILEGES')")
    public ResponseEntity update(
            @Valid @RequestBody Item item,
            @PathVariable Long id
    ) {

        itemService.update(item, id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/supply")
    @PreAuthorize("hasAnyAuthority('SUPPLY_ITEM', 'ALL_PRIVILEGES')")
    public ResponseEntity supply(
            @RequestBody Supply supply,
            @PathVariable Long id
    ) {
        itemService.supply(supply.getQuantity(), id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_ITEM', 'ALL_PRIVILEGES')")
    public ResponseEntity deleteById(
            @PathVariable Long id
    ) {

        itemService.deleteById(id);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("history")
    @PreAuthorize("hasAnyAuthority('SHOW_HISTORY', 'ALL_PRIVILEGES')")
    public ResponseEntity<List<Revisions<Integer, Item>>> history() {
        List<Revisions<Integer, Item>> result = itemService.findAllHistory();

        return ResponseEntity.ok(result);
    }
}
