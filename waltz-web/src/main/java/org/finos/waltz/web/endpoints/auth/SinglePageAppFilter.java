package org.finos.waltz.web.endpoints.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class SinglePageAppFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(SinglePageAppFilter.class);
    private static final String[] EXCLUDED_PATHS = {
            "/api/",
            "/static/",
            "/js/",
            "/css/",
            "/img/",
            "/assets/",
            "/favicon.ico"
    };

    private static final Pattern FILE_EXTENSION_PATTERN = Pattern.compile(".*\\..*"); // Matches .js, .css files, etc.

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization required
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        boolean isStaticResource = FILE_EXTENSION_PATTERN.matcher(path).matches(); // Matches file extensions
        boolean isExcluded = false;

        // Check if the request matches any excluded path
        for (String excludedPath : EXCLUDED_PATHS) {
            if (path.startsWith(excludedPath)) {
                isExcluded = true;
                break;
            }
        }

        if (!isStaticResource && !isExcluded) {
            // Forward to index.html if it's a non-static and non-API request
            LOG.info("Forwarding to index.html for frontend route");
            request.getRequestDispatcher("/index.html").forward(request, response);
            return;
        }

        // Default behavior: pass the request through
        LOG.info("Default behavior");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // No cleanup required
    }
}