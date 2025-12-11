package org.finos.waltz.web.endpoints.api;

import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.common.DateTimeUtilities;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.assessment_definition.AssessmentDefinition;
import org.finos.waltz.model.assessment_definition.ImmutableAssessmentDefinition;
import org.finos.waltz.service.assessment_definition.AssessmentDefinitionService;
import org.finos.waltz.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.getUsernameForSB;

@RestController
@RequestMapping("/api/assessment-definition")
public class AssessmentDefinitionEndpointController {


    private final AssessmentDefinitionService assessmentDefinitionService;
    private final UserRoleService userRoleService;


    @Autowired
    public AssessmentDefinitionEndpointController(AssessmentDefinitionService assessmentDefinitionService, UserRoleService userRoleService) {
        checkNotNull(assessmentDefinitionService, "assessmentDefinitionService cannot be null");

        this.assessmentDefinitionService = assessmentDefinitionService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/favourites")
    public Set<AssessmentDefinition> findForUser(HttpServletRequest request) {
        return assessmentDefinitionService.findFavouritesForUser(getUsernameForSB(request));
    }

    @GetMapping("/id/{id}")
    public AssessmentDefinition getById(@PathVariable Long id) {
        return assessmentDefinitionService.getById(id);
    }

    @GetMapping("/id/{id}/favourite")
    public Set<AssessmentDefinition> favouriteById(HttpServletRequest request, @PathVariable Long id) {
        return assessmentDefinitionService.addFavourite(id,
                getUsernameForSB(request));
    }

    @DeleteMapping("/id/{id}/favourite")
    public Set<AssessmentDefinition> deleteFavouriteById(HttpServletRequest request, @PathVariable Long id) {
        return assessmentDefinitionService.removeFavourite(id, getUsernameForSB(request));
    }

    @GetMapping()
    public Set<AssessmentDefinition> findAll() {
        return assessmentDefinitionService.findAll();
    }

    @PutMapping()
    public Long save(HttpServletRequest request, @RequestBody AssessmentDefinition body) {
        // TODO return ensureUserHasEditRights(request);
        AssessmentDefinition def = ImmutableAssessmentDefinition
                .copyOf(body)
                .withLastUpdatedAt(DateTimeUtilities.nowUtc())
                .withLastUpdatedBy(getUsernameForSB(request));

        return assessmentDefinitionService.save(def);
    }

    @GetMapping("/kind/{kind}")
    public Set<AssessmentDefinition> findByKind(HttpServletRequest request, @PathVariable EntityKind kind) {
        return assessmentDefinitionService.findByEntityKind(kind);
    }

    @GetMapping("/kind/{kind}/id/{id}")
    public Set<AssessmentDefinition> findByRef(HttpServletRequest request, @PathVariable EntityKind kind,
                                               @PathVariable Long id) {
        return assessmentDefinitionService.findByEntityReference(EntityReference.mkRef(kind, id));
    }

    @DeleteMapping("/id/{id}")
    public boolean deleteById(@PathVariable Long id) {
        //TODO ensureUserHasEditRights(request);
        return assessmentDefinitionService.remove(id);
    }

}
