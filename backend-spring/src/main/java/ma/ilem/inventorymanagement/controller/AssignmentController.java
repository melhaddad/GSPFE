package ma.ilem.inventorymanagement.controller;

import ma.ilem.inventorymanagement.entity.Assignment;
import ma.ilem.inventorymanagement.pojo.AssignmentType;
import ma.ilem.inventorymanagement.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revisions;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users_items")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping
    @PreAuthorize(
            "hasAnyAuthority('SHOW_USER_ITEMS', 'ALL_PRIVILEGES') or " +
                    "(hasAnyAuthority('ACCEPT_ITEM', 'REFUSE_ITEM') and #type == 'REQUESTED') or " +
                    "(hasAuthority('DELIVER_ITEM') and #type == 'ACCEPTED') or " +
                    "(hasAuthority('RETURN_ITEM') and #type == 'DELIVERED') or " +
                    "(hasAuthority('TAKE_ITEM') and #type == 'RETURNING') or " +
                    "(isAuthenticated() and #own == true)"
    )
    public ResponseEntity<List<Assignment>> findAll(
            @RequestParam(required = false) AssignmentType type,
            @RequestParam(defaultValue = "0") boolean own,
            Principal principal
    ) {
        List<Assignment> assignments;

        if (own) {
            String email = principal.getName();

            assignments = assignmentService.findAll(email);
        } else if (type == null) {
            assignments = assignmentService.findAll();
        } else {
            assignments = assignmentService.findAll(type);
        }

        return ResponseEntity.ok().body(assignments);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('REQUEST_ITEM', 'ALL_PRIVILEGES') and #assignment.user.email == authentication.principal")
    public ResponseEntity<Object> request(
            @Valid @RequestBody Assignment assignment
    ) {
        Assignment saved = assignmentService.request(assignment);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}/accept/{item}/{quantity}")
    @PreAuthorize("hasAnyAuthority('ACCEPT_ITEM', 'ALL_PRIVILEGES')")
    public ResponseEntity<Object> accept(
            @PathVariable Long id,
            @PathVariable Long item,
            @PathVariable Long quantity
    ) {
        assignmentService.accept(id, item, quantity);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/refuse")
    @PreAuthorize("hasAnyAuthority('REFUSE_ITEM', 'ALL_PRIVILEGES')")
    public ResponseEntity<Object> refuse(
            @PathVariable Long id
    ) {
        assignmentService.refuse(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/deliver")
    @PreAuthorize("hasAnyAuthority('DELIVER_ITEM', 'ALL_PRIVILEGES')")
    public ResponseEntity<Object> deliver(
            @PathVariable Long id
    ) {
        assignmentService.deliver(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/returning")
    @PreAuthorize("hasAnyAuthority('RETURN_ITEM', 'ALL_PRIVILEGES')")
    public ResponseEntity<Object> returning(
            @PathVariable Long id,
            Principal principal
    ) {
        assignmentService.returning(id, principal.getName());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/return")
    @PreAuthorize("hasAnyAuthority('TAKE_ITEM', 'ALL_PRIVILEGES')")
    public ResponseEntity<Object> returned(
            @PathVariable Long id
    ) {
        assignmentService.returned(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("history")
    @PreAuthorize("hasAnyAuthority('SHOW_HISTORY', 'ALL_PRIVILEGES')")
    public ResponseEntity<List<Revisions<Integer, Assignment>>> history() {
        List<Revisions<Integer, Assignment>> result = assignmentService.findAllHistory();

        return ResponseEntity.ok(result);
    }
}
