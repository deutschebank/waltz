package org.finos.waltz.web.endpoints.api;

import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.datatype.DataType;
import org.finos.waltz.model.datatype.DataTypeMigrationResult;
import org.finos.waltz.model.entity_search.EntitySearchOptions;
import org.finos.waltz.service.data_type.DataTypeService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;

@RestController
@RequestMapping("/api/data-types")
public class DataTypesEndpointController {
    private static final String BASE_URL = WebUtilities.mkPath("api", "data-types");

    private final DataTypeService dataTypeService;
    private final UserRoleService userRoleService;

    @Autowired
    public DataTypesEndpointController(DataTypeService dataTypeService, UserRoleService userRoleService) {
        checkNotNull(dataTypeService, "dataTypeService must not be null");
        checkNotNull(userRoleService, "userRoleService must not be null");
        this.userRoleService = userRoleService;
        this.dataTypeService = dataTypeService;
    }

    @GetMapping()
    List<DataType> getAll() {
        return dataTypeService.findAll();
    }

    @PostMapping("/search")
    Collection<DataType> search(@RequestBody String body) {
        return dataTypeService.search(EntitySearchOptions
                .mkForEntity(EntityKind.DATA_TYPE, body));
    }

    @GetMapping("/id/{id}")
    DataType getDataTypeById(@PathVariable Long id) {
        return dataTypeService.getDataTypeById(id);
    }

    @GetMapping("/code/{code}")
    DataType getDataTypeByCode(@PathVariable String code) {
        return dataTypeService.getDataTypeByCode(code);
    }

    @GetMapping("/suggested/entity/{kind}/{id}")
    Set<DataType> findSuggestedByEntityRef(@PathVariable EntityKind kind, @PathVariable Long id) {
        return dataTypeService.findSuggestedByEntityRef(EntityReference.mkRef(kind, id));
    }

    @GetMapping("/parent-id/{id}")
    Collection<DataType> findSuggestedByEntityRef(@PathVariable Long id) {
        return dataTypeService.findByParentId(id);
    }

    @PostMapping("/migrate/{sourceDataTypeId}/to/{targetDataTypeId}")
    DataTypeMigrationResult migratePath(@PathVariable Long sourceDataTypeId,
                                        @PathVariable Long targetDataTypeId,
                                        @RequestParam Boolean removeSource) {
        //TODO ensureUserHasMigrateAdminRights(request);
        return dataTypeService.migrate(sourceDataTypeId, targetDataTypeId, removeSource);
    }

}
