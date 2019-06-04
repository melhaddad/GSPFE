package ma.ilem.inventorymanagement.controller;

import ma.ilem.inventorymanagement.entity.Role;
import ma.ilem.inventorymanagement.service.RoleService;
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
@RequestMapping("api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SHOW_ROLES', 'ALL_PRIVILEGES')")
    public ResponseEntity<List<Role>> findAll() {
        List<Role> roles = roleService.findAllByDeletedIsFalse();

        return ResponseEntity.ok(roles);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADD_ROLE', 'ALL_PRIVILEGES')")
    public ResponseEntity<Role> save(
            @Valid @RequestBody Role role
    ) {
        Role saved = roleService.save(role);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_ROLE', 'ALL_PRIVILEGES')")
    public ResponseEntity<Object> update(
            @Valid @RequestBody Role role,
            @PathVariable Long id
    ) {
        roleService.update(id, role);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_ROLE', 'ALL_PRIVILEGES')")
    public ResponseEntity<Object> delete(
            @PathVariable Long id
    ) {
        roleService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("history")
    @PreAuthorize("hasAnyAuthority('SHOW_HISTORY', 'ALL_PRIVILEGES')")
    public ResponseEntity<List<Revisions<Integer, Role>>> history() {
        List<Revisions<Integer, Role>> result = roleService.findAllHistory();

        return ResponseEntity.ok(result);
    }
}
