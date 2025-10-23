package org.finos.waltz.web.endpoints.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.Map;

public class AuthConfig {
    @Bean
    public FilterRegistrationBean<JWTAuthenticationFilter> loggingFilterRegistration() {
        FilterRegistrationBean<JWTAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JWTAuthenticationFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);
        registrationBean.setName("JWTFilter");
        return registrationBean;
    }
}
