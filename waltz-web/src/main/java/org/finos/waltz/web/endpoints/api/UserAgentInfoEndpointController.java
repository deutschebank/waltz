package org.finos.waltz.web.endpoints.api;

import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.common.DateTimeUtilities;
import org.finos.waltz.model.user_agent_info.ImmutableUserAgentInfo;
import org.finos.waltz.model.user_agent_info.UserAgentInfo;
import org.finos.waltz.service.user_agent_info.UserAgentInfoService;
import org.finos.waltz.web.WebUtilities;
import org.finos.waltz.web.json.BrowserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
@RestController
@RequestMapping("/api/user-agent-info")
public class UserAgentInfoEndpointController {

    private static final int DEFAULT_LIMIT = 10;

    private final UserAgentInfoService userAgentInfoService;


    @Autowired
    public UserAgentInfoEndpointController(UserAgentInfoService userAgentInfoService) {
        checkNotNull(userAgentInfoService, "userAgentInfoService cannot be null");
        this.userAgentInfoService = userAgentInfoService;
    }

    @GetMapping("/user/{userName}")
    public List<UserAgentInfo> findForUser(@PathVariable String userName, @RequestParam String limitStr){

        int limit = limitStr == null || limitStr.equals("")
                ? DEFAULT_LIMIT
                : Integer.parseInt(limitStr);

        return userAgentInfoService.findLoginsForUser(userName, limit);
    }

    @PostMapping()
    public Integer recordLogin(HttpServletRequest request, @RequestBody BrowserInfo browserInfo){

        String userAgent = request.getHeader("User-Agent");
        UserAgentInfo userAgentInfo = ImmutableUserAgentInfo.builder()
                .userName(WebUtilities.getUsernameForSB(request))
                .userAgent(userAgent)
                .operatingSystem(browserInfo.operatingSystem())
                .resolution(browserInfo.resolution())
                .loginTimestamp(DateTimeUtilities.nowUtc())
                .ipAddress(request.getRemoteAddr())
                .build();

        return userAgentInfoService.save(userAgentInfo);
    }
}
