package org.finos.waltz.web.endpoints.api;

import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.legal_entity.LegalEntityRelationship;
import org.finos.waltz.model.legal_entity.LegalEntityRelationshipView;
import org.finos.waltz.service.legal_entity.LegalEntityRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.mkPath;

@RestController
@RequestMapping("/api/legal-entity-relationship/")
public class LegalEntityRelationshipController {

    public static final String BASE_URL = mkPath("api", "legal-entity-relationship");

    private final LegalEntityRelationshipService legalEntityRelationshipService;

    @Autowired
    public LegalEntityRelationshipController(LegalEntityRelationshipService legalEntityRelationshipService) {
        checkNotNull(legalEntityRelationshipService, "legalEntityRelationshipService cannot be null");
        this.legalEntityRelationshipService = legalEntityRelationshipService;
    }

    @GetMapping("id/{id}")
    public LegalEntityRelationship getById(@PathVariable("id") long id) {
        return legalEntityRelationshipService.getById(id);
    }

    @GetMapping("legal-entity-id/{id}")
    public Set<LegalEntityRelationship> findByLegalEntityId(@PathVariable("id") long id) {
        return legalEntityRelationshipService.findByLegalEntityId(id);
    }

    @GetMapping("relationship-kind/{id}")
    public Set<LegalEntityRelationship> findByRelationshipKindId(@PathVariable("id") long id) {
        return legalEntityRelationshipService.findByRelationshipKindId(id);
    }

    @GetMapping("kind/{kind}/id/{id}")
    public Set<LegalEntityRelationship> findByEntityReference(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        return legalEntityRelationshipService.findByEntityReference(EntityReference.mkRef(EntityKind.valueOf(kind), id));
    }

    @PostMapping("relationship-kind/{id}/view")
    public LegalEntityRelationshipView getViewByRelKindAndSelector(@PathVariable("id") long id, @RequestBody IdSelectionOptions idSelectionOptions) {
        return legalEntityRelationshipService.getViewByRelKindAndSelector(id, idSelectionOptions);
    }

}
