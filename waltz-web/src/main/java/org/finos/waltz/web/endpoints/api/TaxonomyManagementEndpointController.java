package org.finos.waltz.web.endpoints.api;

import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.common.EnumUtilities;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.bulk_upload.BulkUpdateMode;
import org.finos.waltz.model.bulk_upload.taxonomy.BulkTaxonomyApplyResult;
import org.finos.waltz.model.bulk_upload.taxonomy.BulkTaxonomyValidationResult;
import org.finos.waltz.model.taxonomy_management.TaxonomyChangeCommand;
import org.finos.waltz.model.taxonomy_management.TaxonomyChangePreview;
import org.finos.waltz.service.taxonomy_management.BulkTaxonomyChangeService;
import org.finos.waltz.service.taxonomy_management.BulkTaxonomyItemParser;
import org.finos.waltz.service.taxonomy_management.TaxonomyChangeService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.finos.waltz.web.WebUtilities.getUsernameForSB;

@RestController
@RequestMapping("/api/taxonomy-management")
public class TaxonomyManagementEndpointController {

    private final TaxonomyChangeService taxonomyChangeService;
    private final BulkTaxonomyChangeService bulkTaxonomyChangeService;


    @Autowired
    public TaxonomyManagementEndpointController(TaxonomyChangeService taxonomyChangeService,
                                                BulkTaxonomyChangeService bulkTaxonomyChangeService) {
        this.taxonomyChangeService = taxonomyChangeService;
        this.bulkTaxonomyChangeService = bulkTaxonomyChangeService;
    }

    @PostMapping("/preview")
    TaxonomyChangePreview registerPreview(@RequestBody TaxonomyChangeCommand body) {
        return taxonomyChangeService.preview(body);
    }

    @PostMapping("/pending-changes")
    TaxonomyChangeCommand registerSubmitPendingChange(HttpServletRequest request, @RequestBody TaxonomyChangeCommand body, @RequestParam Long id) {
        return taxonomyChangeService.submitDraftChange(body, WebUtilities.getUsernameForSB(request));
    }

    @DeleteMapping("/pending-changes/id/{id}")
    Boolean registerRemoveById(HttpServletRequest request, @PathVariable Long id) {
        return taxonomyChangeService.removeById(id,
                WebUtilities.getUsernameForSB(request));
    }

    @GetMapping("/pending-changes/id/{id}/preview")
    TaxonomyChangePreview registerPreviewById(@PathVariable Long id) {
        return taxonomyChangeService.previewById(id);
    }

    @PostMapping("/pending-changes/id/{id}/apply")
    TaxonomyChangeCommand registerApplyPendingChange(HttpServletRequest request, @PathVariable Long id) {
        return taxonomyChangeService.applyById(id,
                WebUtilities.getUsernameForSB(request));
    }

    @GetMapping("/pending-changes/by-domain/{kind}/{id}")
    Collection<TaxonomyChangeCommand> registerApplyPendingChange(@PathVariable EntityKind kind, @PathVariable Long id) {
        return taxonomyChangeService.findDraftChangesByDomain(EntityReference.mkRef(kind, id));
    }

    @GetMapping("/all/by-domain/{kind}/{id}")
    Collection<TaxonomyChangeCommand> registerFindAllChangesByDomain(@PathVariable EntityKind kind, @PathVariable Long id) {
        return taxonomyChangeService.findAllChangesByDomain(EntityReference.mkRef(kind, id));
    }

    @PostMapping("/bulk/preview/{kind}/{id}")
    BulkTaxonomyValidationResult registerFindAllChangesByDomain(@PathVariable EntityKind kind,
                                                                @PathVariable Long id,
                                                                @RequestParam("format") String formatInput,
                                                                @RequestParam("mode") String modeInput,
                                                                @RequestBody String body) {
        EntityReference taxonomyRef = EntityReference.mkRef(kind, id);

        BulkTaxonomyItemParser.InputFormat format = EnumUtilities.readEnum(formatInput, BulkTaxonomyItemParser.InputFormat.class, s -> BulkTaxonomyItemParser.InputFormat.TSV);

        BulkUpdateMode mode = EnumUtilities.readEnum(modeInput, BulkUpdateMode.class, s -> BulkUpdateMode.ADD_ONLY);

        return bulkTaxonomyChangeService.previewBulk(taxonomyRef, body, format, mode);
    }

    @PostMapping("/bulk/apply/{kind}/{id}")
    BulkTaxonomyApplyResult registerFindAllChangesByDomain(@PathVariable EntityKind kind,
                                                           @PathVariable Long id,
                                                           @RequestParam("format") String formatInput,
                                                           @RequestParam("mode") String modeInput,
                                                           @RequestBody String body,
                                                           HttpServletRequest req) {
        String userId = getUsernameForSB(req);
        EntityReference taxonomyRef = EntityReference.mkRef(kind, id);

        BulkTaxonomyItemParser.InputFormat format = EnumUtilities.readEnum(formatInput, BulkTaxonomyItemParser.InputFormat.class, s -> BulkTaxonomyItemParser.InputFormat.TSV);

        BulkUpdateMode mode = EnumUtilities.readEnum(modeInput, BulkUpdateMode.class, s -> BulkUpdateMode.ADD_ONLY);
        BulkTaxonomyValidationResult validationResult = bulkTaxonomyChangeService.previewBulk(taxonomyRef, body, format, mode);
        return bulkTaxonomyChangeService.applyBulk(taxonomyRef, validationResult, userId);
    }


}
