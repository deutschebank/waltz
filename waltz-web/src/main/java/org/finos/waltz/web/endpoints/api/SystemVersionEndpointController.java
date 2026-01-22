package org.finos.waltz.web.endpoints.api;

import org.finos.waltz.model.system_version.SystemVersionInfo;
import org.finos.waltz.service.system_version.SystemVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.finos.waltz.web.WebUtilities.mkPath;

@RestController
@RequestMapping(SystemVersionEndpointController.BASE_URL)
public class SystemVersionEndpointController {

    public static final String BASE_URL = "/api/system-version/";

    private final SystemVersionService systemVersionService;


    @Autowired
    public SystemVersionEndpointController(SystemVersionService systemVersionService) {
        this.systemVersionService = systemVersionService;
    }

    @GetMapping()
    public SystemVersionInfo getVersionInfo() {
        return systemVersionService.getVersionInfo();
    }
}
