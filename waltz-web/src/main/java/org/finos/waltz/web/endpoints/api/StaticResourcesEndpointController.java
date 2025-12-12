/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019 Waltz open source project
 * See README.md for more information
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific
 *
 */
package org.finos.waltz.web.endpoints.api;

import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.web.HttpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.finos.waltz.common.IOUtilities.copyStream;
import static org.finos.waltz.common.IOUtilities.readLines;
import static org.finos.waltz.common.StringUtilities.lower;
import static org.finos.waltz.common.StringUtilities.notEmpty;
import static org.finos.waltz.web.WebUtilities.getMimeType;

@RestController
@RequestMapping("/")
public class StaticResourcesEndpointController {
    private static final Logger LOG = LoggerFactory.getLogger(StaticResourcesEndpointController.class);
    private static final String CACHE_MAX_AGE_VALUE = "max-age=" + TimeUnit.DAYS.toSeconds(30);


    private final ClassLoader classLoader = StaticResourcesEndpointController.class
            .getClassLoader();


    @GetMapping("/**")
    public Object register(HttpServletRequest request) {
        LOG.debug("Registering static resources");

        String resolvedPath = resolvePath(request);

        if (resolvedPath == null) {
            return null;
        }

        try (
                InputStream resourceAsStream = classLoader.getResourceAsStream(resolvedPath)
        ) {
            if (resourceAsStream == null) {
                return null;
            } else {
                String message = format(
                        "Serving %s in response to request for %s",
                        resolvedPath,
                        request.getRequestURI());
                LOG.info(message);


                HttpHeaders headers = addCacheHeadersIfNeeded(resolvedPath);
                InputStream modifiedStream = modifyIndexBaseTagIfNeeded(request, resolvedPath, resourceAsStream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                copyStream(modifiedStream, byteArrayOutputStream); // Copy stream content to output
                byte[] fileBytes = byteArrayOutputStream.toByteArray();
                String mimeType = getMimeType(resolvedPath);

                ResponseEntity<byte[]> body = ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaType.parseMediaType(mimeType))
                        .body(fileBytes);
                return
                        body; // Serve the resource content
            }
        } catch (Exception e) {
            LOG.warn("Encountered error when attempting to serve: " + resolvedPath, e);
            return null;
        }
    }


    /**
     * We want to add a cache-control: max-age value to all resources except html.
     * This is because the html resources have references to 'cache-busted' js files
     * and other resources.  If the html was also cached then it would be difficult
     * to detect client code updates.
     *
     * @param resolvedPath - the resolved path to the resource we are serving
     */
    private HttpHeaders addCacheHeadersIfNeeded(String resolvedPath) {
        HttpHeaders headers = new HttpHeaders();
        if (!resolvedPath.endsWith(".html")) {
            headers.add(HttpHeader.CACHE_CONTROL, CACHE_MAX_AGE_VALUE);
        }
        return headers;
    }


    /**
     * index.html need to have a <base href="/[site_context]/" /> tag in the head section to ensure
     * html5 mode works correctly in AngularJS.  This method will ensure the existing <base href="/" /> tag
     * is replace with one that includes the correct site context as deployed.
     *
     * @param request
     * @param resolvedPath
     * @param resourceStream
     * @return the modified input stream with the amended <base> tag or the original unmodified resourceStream
     * @throws IOException
     */
    private InputStream modifyIndexBaseTagIfNeeded(HttpServletRequest request,
                                                   String resolvedPath,
                                                   InputStream resourceStream) throws IOException {
        if (resolvedPath.endsWith("index.html") && notEmpty(request.getContextPath())) {
            List<String> lines = readLines(resourceStream);

            for (int i = 0; i < lines.size(); i++) {
                String line = lower(lines.get(i));

                if (line.contains("<base href=")) {
                    LOG.info("Found <base> tag: " + line + ", adding context path: " + request.getContextPath());
                    line = line.replaceFirst(
                            "<base href=(['\"])/(['\"])\\s*/>",
                            format(
                                    "\t<base href=\"%s/\" />",
                                    request.getContextPath()));
                    LOG.info("Updated <base> tag: " + line);
                    lines.set(i, line);

                    // done, exit loop
                    break;
                }

                if (line.contains("</head>")) {
                    // don't need to continue if have reached here and no base tag found
                    break;
                }
            }

            // copy amended file into a stream
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {

                for (String line : lines) {
                    writer.write(line);
                    writer.write(System.lineSeparator());
                }

                writer.flush();
                return new ByteArrayInputStream(outputStream.toByteArray());
            }
        }
        return resourceStream;
    }


    private String resolvePath(HttpServletRequest request) {
        final String indexPath = "static/index.html";
        String path = request.getRequestURI().replaceFirst("/", "");
        String resourcePath = path.length() > 0 ? ("static/" + path) : indexPath;

        URL resource = classLoader.getResource(resourcePath);

        if (resource == null) {
            // 404: return index.html
            resource = classLoader.getResource(indexPath);
            resourcePath = indexPath;
        }

        boolean isDirectory = resource
                .getPath()
                .endsWith("/");

        return isDirectory
                ? resourcePath + "/index.html"
                : resourcePath;
    }

}
