package org.finos.waltz.web.endpoints.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.model.user.UserUtilities;
import org.finos.waltz.service.user.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;


public class UserContextFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(UserContextFilter.class);

    private final UserRoleService userRoleService;


    public UserContextFilter(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.info("Start of UserContextFilter#doFilter()");

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        String userName = AuthenticationUtilities.getUsernameForSB(httpRequest);
        if (!UserUtilities.ANONYMOUS_USERNAME.equals(userName)) {
            authorities = userRoleService.getUserRoles(userName).stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                userName,
                "",
                authorities
        );
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}