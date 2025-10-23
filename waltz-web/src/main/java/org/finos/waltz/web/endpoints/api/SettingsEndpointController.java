package org.finos.waltz.web.endpoints.api;

import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.model.settings.ImmutableSetting;
import org.finos.waltz.model.settings.Setting;
import org.finos.waltz.model.settings.UpdateSettingsCommand;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.settings.SettingsService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.finos.waltz.common.CollectionUtilities.map;
import static org.finos.waltz.web.WebUtilities.mkPath;

@RestController
@RequestMapping("/api/settings")
public class SettingsEndpointController {

    private final SettingsService settingsService;
    private final UserRoleService userRoleService;


    @Autowired
    public SettingsEndpointController(SettingsService settingsService,
                                      UserRoleService userRoleService) {
        this.settingsService = settingsService;
        this.userRoleService = userRoleService;
    }

    @GetMapping()
    Collection<Setting> findAll(HttpServletRequest request) {
        Collection<Setting> settings = settingsService.findAll();
        return isAdmin(request) ? settings : sanitize(settings);
    }

    @GetMapping("/name/{name}")
    Setting getByName(HttpServletRequest request, @PathVariable String name) {
        Setting setting = settingsService.getByName(name);
        return isAdmin(request) ? setting : sanitize(setting);
    }

    @PostMapping("/update")
    Integer updateValue(@RequestBody UpdateSettingsCommand updateCommand) {
        //TODO: requireRole(userRoleService, request, SystemRole.ADMIN);
        return settingsService.update(updateCommand);
    }

    @PostMapping("/create")
    Integer createValue(@RequestBody Setting body) {
        //TODO: requireRole(userRoleService, request, SystemRole.ADMIN);
        return settingsService.create(body);
    }


    private boolean isAdmin(HttpServletRequest request) {
        return userRoleService.hasRole(WebUtilities.getUsernameForSB(request), SystemRole.ADMIN);
    }


    private Collection<Setting> sanitize(Collection<Setting> settings) {
        return map(settings, s -> sanitize(s));
    }


    private Setting sanitize(Setting s) {
        return s.restricted()
                ? ImmutableSetting.copyOf(s).withValue("****")
                : s;
    }
}
