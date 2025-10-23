package org.finos.waltz.web.endpoints.api;

import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.thumbnail.Thumbnail;
import org.finos.waltz.model.thumbnail.ThumbnailSaveCommand;
import org.finos.waltz.service.thumbnail.ThumbnailService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.finos.waltz.common.Checks.checkNotNull;

@RestController
@RequestMapping("/api/thumbnail")
public class ThumbnailEndpointController {

    private final String BASE_URL = WebUtilities.mkPath("api", "thumbnail");
    private final ThumbnailService thumbnailService;

    @Autowired
    public ThumbnailEndpointController(ThumbnailService thumbnailService) {
        checkNotNull(thumbnailService, "thumbnailService cannot be null");
        this.thumbnailService = thumbnailService;
    }

    @GetMapping("/{kind}/{id}")
    public Thumbnail find(@PathVariable EntityKind kind, @PathVariable Long id) {
        return thumbnailService.getByReference(EntityReference.mkRef(kind, id));

    }

    @DeleteMapping("/{kind}/{id}")
    public Boolean delete(HttpServletRequest request, @PathVariable EntityKind kind, @PathVariable Long id) {
        return thumbnailService.deleteByReference(EntityReference.mkRef(kind, id), WebUtilities.getUsernameForSB(request));
    }

    @PostMapping("/save")
    public void save(HttpServletRequest request, @RequestBody ThumbnailSaveCommand body) {
        thumbnailService.save(body, WebUtilities.getUsernameForSB(request));
    }

}
