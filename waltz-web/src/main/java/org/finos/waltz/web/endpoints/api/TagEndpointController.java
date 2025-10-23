package org.finos.waltz.web.endpoints.api;

import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.tag.Tag;
import org.finos.waltz.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.getUsernameForSB;
import static org.finos.waltz.web.WebUtilities.mkPath;

@RestController
@RequestMapping("/api/tag")
public class TagEndpointController {

    private static final String BASE_URL = mkPath("api", "tag");
    private final TagService tagService;


    @Autowired
    public TagEndpointController(TagService tagService) {
        checkNotNull(tagService, "tagService cannot be null");
        this.tagService = tagService;
    }

    @GetMapping("/id/{id}")
    Tag registerPreview(@PathVariable Long id) {
        return tagService.getById(id);
    }

    @GetMapping("/entity/{kind}/{id}")
    List<Tag> findTagsForEntityReference(@PathVariable EntityKind kind, @PathVariable Long id) {
        return tagService.findTagsForEntityReference(EntityReference.mkRef(kind, id));
    }

    @GetMapping("/target-kind/{kind}")
    List<Tag> findTagsForEntityKind(@PathVariable EntityKind kind) {
        return tagService.findTagsForEntityKind(kind);
    }

    @PostMapping("/target-kind/{kind}/target-selector")
    List<Tag> findTagsForEntityKindAndTargetSelector(@RequestBody IdSelectionOptions body, @PathVariable EntityKind kind) {
        return tagService.findTagsForEntityKindAndTargetSelector(kind, body);
    }

    @PostMapping("/entity/{kind}/{id}")
    List<Tag> updateRoute(HttpServletRequest request,
                          @PathVariable EntityKind kind,
                          @PathVariable Long id,
                          @RequestBody List<String> body) {
        String username = getUsernameForSB(request);
        EntityReference ref = EntityReference.mkRef(kind, id);
        return tagService.updateTags(ref, body, username);
    }

}
