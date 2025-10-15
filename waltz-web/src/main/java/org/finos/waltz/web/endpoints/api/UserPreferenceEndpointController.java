package org.finos.waltz.web.endpoints.api;

import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.model.user.UserPreference;
import org.finos.waltz.service.user.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.endpoints.auth.AuthenticationUtilities.getUsernameForSB;


@RestController
@RequestMapping("/api/user-preference")
public class UserPreferenceEndpointController {
    private final UserPreferenceService userPreferenceService;

    @Autowired
    public UserPreferenceEndpointController(UserPreferenceService userPreferenceService) {
        checkNotNull(userPreferenceService, "userPreferenceService cannot be null");
        this.userPreferenceService = userPreferenceService;
    }


    @GetMapping()
    public Object findAllForUser(HttpServletRequest request) {
        String userName = getUsernameForSB(request);
        return userPreferenceService.getPreferences(userName);
    }

    @PostMapping("/save-all")
    public Object saveAllForUser(HttpServletRequest request, @RequestBody List<UserPreference> preferences) {
        String userName = getUsernameForSB(request);
        return userPreferenceService.savePreferences(userName, preferences);
    }

    @PostMapping("/save")
    public Object saveForUser(HttpServletRequest request, @RequestBody UserPreference preferences) {
        String userName = getUsernameForSB(request);
        return userPreferenceService.savePreference(userName, preferences);
    }

    @DeleteMapping("/clear")
    public Object saveForUser(HttpServletRequest request) {
        String userName = getUsernameForSB(request);
        return userPreferenceService.clearPreferences(userName);
    }

}
