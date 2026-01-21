package org.finos.waltz.web.endpoints.api;

import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.legal_entity.LegalEntityRelKindStat;
import org.finos.waltz.model.legal_entity.LegalEntityRelationshipKind;
import org.finos.waltz.service.legal_entity.LegalEntityRelationshipKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;

@RestController
@RequestMapping("/api/legal-entity-relationship-kind")
public class LegalEntityRelationshipKindController {

    private final LegalEntityRelationshipKindService legalEntityRelationshipKindService;

    @Autowired
    public LegalEntityRelationshipKindController(LegalEntityRelationshipKindService legalEntityRelationshipKindService) {
        checkNotNull(legalEntityRelationshipKindService, "legalEntityRelationshipKindService cannot be null");
        this.legalEntityRelationshipKindService = legalEntityRelationshipKindService;
    }

    @GetMapping("/id/{id}")
    public LegalEntityRelationshipKind getById(@PathVariable("id") long id) {
        return legalEntityRelationshipKindService.getById(id);
    }

    @GetMapping()
    public Set<LegalEntityRelationshipKind> findAll() {
        return legalEntityRelationshipKindService.findAll();
    }

    @GetMapping("/stats")
    public Set<LegalEntityRelKindStat> findUsageStats() {
        return legalEntityRelationshipKindService.findUsageStats();
    }

    @PostMapping("/stats/relationship-kind/{id}/selector")
    public LegalEntityRelKindStat findUsageStatsByRelKindAndSelector(@PathVariable("id") long id,
                                                                     @RequestBody IdSelectionOptions selectionOptions) throws IOException {
        return legalEntityRelationshipKindService.getUsageStatsByKindAndSelector(id, selectionOptions);
    }
}
