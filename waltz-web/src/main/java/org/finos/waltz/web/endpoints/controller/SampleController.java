package org.finos.waltz.web.endpoints.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class SampleController {
    private static final Logger LOG = LoggerFactory.getLogger(SampleController.class);

    @GetMapping("sample")
    public String sayHello() {
        LOG.info("API Invoked");
        return "Hello, Spring Boot!";
    }
}
