package org.finos.waltz.web.endpoints.api;

import org.finos.waltz.model.user_agent_info.UserAgentInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/uid")
public class UIDEndpointController {
    @GetMapping("/generate-one")
    public String findForUser(){
        return UUID.randomUUID().toString();
    }
}
