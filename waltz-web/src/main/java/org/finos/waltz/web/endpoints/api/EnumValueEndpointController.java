package org.finos.waltz.web.endpoints.api;

import org.finos.waltz.model.EnumValue;
import org.finos.waltz.service.enum_value.EnumValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;

@RestController
@RequestMapping("/api/enum-value")
public class EnumValueEndpointController {
    private final EnumValueService enumValueService;


    @Autowired
    public EnumValueEndpointController(EnumValueService enumValueService) {
        checkNotNull(enumValueService, "enumValueService cannot be null");
        this.enumValueService = enumValueService;
    }

    @GetMapping
    List<EnumValue> getAll() {
        return enumValueService.findAll();
    }

}
