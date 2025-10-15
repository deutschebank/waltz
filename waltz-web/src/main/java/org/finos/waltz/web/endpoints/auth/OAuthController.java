package org.finos.waltz.web.endpoints.auth;

import org.finos.waltz.service.settings.SettingsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OAuthController {
    private final static String OAUTH_PROVIDER_DETAILS = "oauth.provider.details";
    private final SettingsService settingsService;
    public OAuthController(SettingsService settingsService){
        this.settingsService = settingsService;
    }

    @GetMapping("/oauthdetails")
    ResponseEntity<String> getDetails(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/javascript"));
        Optional<String> OauthProviderDetails = Optional.of(settingsService
                .getValue(OAUTH_PROVIDER_DETAILS)
                .orElse("{name : null}"));
        return new ResponseEntity<>("const oauthdetails = " + OauthProviderDetails.get() + ";", headers, HttpStatus.OK);
    }
}
