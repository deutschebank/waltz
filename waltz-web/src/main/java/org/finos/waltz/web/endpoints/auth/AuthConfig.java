package org.finos.waltz.web.endpoints.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;
import org.finos.waltz.model.settings.NamedSettings;
import org.finos.waltz.service.settings.SettingsService;
import org.finos.waltz.service.user.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AuthConfig {
    private static final Logger LOG = LoggerFactory.getLogger(AuthConfig.class);
    private final SettingsService settingsService;
    private final UserRoleService userRoleService;
    private final ObjectMapper mapper;


    @Autowired
    public AuthConfig(SettingsService settingsService, UserRoleService userRoleService, ObjectMapper mapper) {
        this.settingsService = settingsService;
        this.userRoleService = userRoleService;
        this.mapper = mapper;
    }

    @Bean
    public FilterRegistrationBean<Filter> dynamicServletFilterRegistration() {

        Filter filter = settingsService
                .getValue(NamedSettings.authenticationFilter)
                .flatMap(this::instantiateFilter)
                .orElseGet(createDefaultFilter());

        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        registrationBean.setName("AuthenticationFilter");
        return registrationBean;
    }


    private Optional<Filter> instantiateFilter(String className) {
        try {
            LOG.info("Setting authentication filter to: {}", className);

            Filter filter = (Filter) Class
                    .forName(className)
                    .getConstructor(SettingsService.class)
                    .newInstance(settingsService);

            return Optional.of(filter);
        } catch (Exception e) {
            LOG.error("Cannot instantiate authentication filter class: {} ", className, e);
            return Optional.empty();
        }
    }

    private Supplier<Filter> createDefaultFilter() {
        return () -> {
            LOG.info("Using default (jwt) authentication filter");
            return new JWTAuthenticationFilter(settingsService);
        };
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();

    }


    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> {
            throw new UnsupportedOperationException("Custom authentication used.");
        };
    }

    @Bean
    public FilterRegistrationBean<UserContextFilter> userContextFilterRegistration() {
        FilterRegistrationBean<UserContextFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserContextFilter(userRoleService));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("UserContextFilter");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
