package org.finos.waltz.web.endpoints.api;

import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.legal_entity.LegalEntity;
import org.finos.waltz.service.legal_entity.LegalEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;

@RestController
@RequestMapping("/api/legal-entity")
public class LegalEntityEndpointController {


    private final LegalEntityService legalEntityService;

    @Autowired
    public LegalEntityEndpointController(LegalEntityService legalEntityService) {
        checkNotNull(legalEntityService, "legalEntityService cannot be null");
        this.legalEntityService = legalEntityService;
    }

    @GetMapping("/id/{id}")
    public LegalEntity getById(@PathVariable("id") long id) {
        return legalEntityService.getById(id);
    }

    @PostMapping("/selector")
    public Set<LegalEntity> findBySelector(@RequestBody IdSelectionOptions selectionOptions) throws IOException {
        return legalEntityService.findBySelector(selectionOptions);
    }

    @GetMapping("/all")
    public Collection<LegalEntity> findAll() throws IOException {
        return legalEntityService.findAll();
    }

}
