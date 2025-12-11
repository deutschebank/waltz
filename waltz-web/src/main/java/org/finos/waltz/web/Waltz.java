package org.finos.waltz.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication(scanBasePackages = {"org.finos.waltz"}, exclude = { JooqAutoConfiguration.class} )
public class Waltz extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Waltz.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Waltz.class);
    }
}
