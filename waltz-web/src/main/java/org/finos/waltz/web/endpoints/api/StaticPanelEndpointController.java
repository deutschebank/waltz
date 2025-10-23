package org.finos.waltz.web.endpoints.api;

import org.finos.waltz.model.staticpanel.StaticPanel;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.static_panel.StaticPanelService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;

@RestController
@RequestMapping("/api/static-panel")
public class StaticPanelEndpointController {


    private final StaticPanelService staticPanelService;
    private final UserRoleService userRoleService;


    @Autowired
    public StaticPanelEndpointController(StaticPanelService staticPanelService, UserRoleService userRoleService) {
        checkNotNull(staticPanelService, "staticPanelService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");
        this.staticPanelService = staticPanelService;
        this.userRoleService = userRoleService;
    }

    @GetMapping()
    List<StaticPanel> findAll() {
        return staticPanelService.findAll();
    }

    @GetMapping("/group")
    List<StaticPanel> findByGroups(@RequestParam List<String> group) {
        return staticPanelService.findByGroups(group.toArray(String[]::new));
    }

    @PostMapping()
    Boolean save(@RequestBody StaticPanel panel) {
        //TODO: WebUtilities.requireRole(userRoleService, request, SystemRole.ADMIN);
        return staticPanelService.save(panel);
    }

    private boolean saveRoute(Request request, Response response) throws IOException {
        WebUtilities.requireRole(userRoleService, request, SystemRole.ADMIN);
        StaticPanel panel = WebUtilities.readBody(request, StaticPanel.class);
        return staticPanelService.save(panel);
    }
}
