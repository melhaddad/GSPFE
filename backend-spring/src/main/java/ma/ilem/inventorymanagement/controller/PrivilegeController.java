package ma.ilem.inventorymanagement.controller;

import ma.ilem.inventorymanagement.entity.Privilege;
import ma.ilem.inventorymanagement.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revisions;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/privileges")
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Privilege>> findAll() {
        List<Privilege> result = privilegeService.findAll();

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_PRIVILEGE', 'ALL_PRIVILEGES')")
    public ResponseEntity<Object> update(
            @Valid @RequestBody Privilege privilege,
            @PathVariable Long id
    ) {
        privilegeService.update(privilege, id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/enable")
    @PreAuthorize("hasAnyAuthority('ENABLE_PRIVILEGE', 'ALL_PRIVILEGES')")
    public ResponseEntity enable(
            @PathVariable Long id
    ) {
        privilegeService.enable(true, id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/disable")
    @PreAuthorize("hasAnyAuthority('DISABLE_PRIVILEGE', 'ALL_PRIVILEGES')")
    public ResponseEntity disable(
            @PathVariable Long id
    ) {
        privilegeService.enable(false, id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("history")
    @PreAuthorize("hasAnyAuthority('SHOW_HISTORY', 'ALL_PRIVILEGES')")
    public ResponseEntity<List<Revisions<Integer, Privilege>>> history() {
        List<Revisions<Integer, Privilege>> result = privilegeService.findAllHistory();

        return ResponseEntity.ok(result);
    }
}
