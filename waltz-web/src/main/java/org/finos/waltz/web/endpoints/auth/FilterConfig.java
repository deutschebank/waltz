package org.finos.waltz.web.endpoints.auth;

import jakarta.servlet.Filter;
import org.finos.waltz.model.settings.NamedSettings;
import org.finos.waltz.service.settings.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class FilterConfig {
    private static final Logger LOG = LoggerFactory.getLogger(FilterConfig.class);
    SettingsService settingsService;

    @Autowired
    public FilterConfig(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @Bean
    public FilterRegistrationBean<Filter> dynamicServletFilterRegistration() {
        Optional<String> isFilterEnabled = settingsService.getValue(NamedSettings.authenticationFilter);
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        if (isFilterEnabled.isPresent()) {
            registrationBean.setFilter(new HeaderBasedAuthenticationFilter(settingsService));
            registrationBean.addUrlPatterns("/*");
            registrationBean.setName("HeaderBasedAuthenticationFilter");
            LOG.info("HeaderBasedAuthenticationFilter Enabled");
            return registrationBean;
        } else {
            registrationBean.setFilter(new JWTAuthenticationFilter());
            registrationBean.addUrlPatterns("/*");
            registrationBean.setName("JWTAuthenticationFilter");
            LOG.info("JWTAuthenticationFilter Enabled");
        }
        return registrationBean;
    }
}
