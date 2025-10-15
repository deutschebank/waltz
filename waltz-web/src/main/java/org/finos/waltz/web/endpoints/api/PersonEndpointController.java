package org.finos.waltz.web.endpoints.api;

import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.model.person.Person;
import org.finos.waltz.model.person.PersonKind;
import org.finos.waltz.service.person.PersonService;
import org.finos.waltz.service.person_hierarchy.PersonHierarchyService;
import org.finos.waltz.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.getUsernameForSB;
import static org.finos.waltz.web.WebUtilities.mkPath;

@RestController
@RequestMapping("/api/person")
public class PersonEndpointController {
    private static final String BASE_URL = mkPath("api", "person");
    private static final String SEARCH_PATH = mkPath(BASE_URL, "search", ":query");
    private static final String DIRECTS_PATH = mkPath(BASE_URL, "employee-id", ":empId", "directs");
    private static final String COUNT_CUMULATIVE_REPORTS_BY_KIND_PATH = mkPath(BASE_URL, "employee-id", ":empId", "count-cumulative-reports");
    private static final String MANAGERS_PATH = mkPath(BASE_URL, "employee-id", ":empId", "managers");
    private static final String BY_EMPLOYEE_PATH = mkPath(BASE_URL, "employee-id", ":empId");
    private static final String GET_BY_USERID_PATH = mkPath(BASE_URL, "user-id", ":userId");
    private static final String GET_SELF_PATH = mkPath(BASE_URL, "self");
    private static final String GET_BY_ID = mkPath(BASE_URL, "id", ":id");
    private static final String REBUILD_HIERARCHY_PATH = mkPath(BASE_URL, "rebuild-hierarchy");
    private static final String DIRECTS_FOR_PERSON_IDS_PATH = mkPath(BASE_URL, "person-ids", "directs");
    private final PersonService personService;
    private final PersonHierarchyService personHierarchyService;
    private final UserRoleService userRoleService;


    @Autowired
    public PersonEndpointController(PersonService service,
                                    PersonHierarchyService personHierarchyService,
                                    UserRoleService userRoleService) {
        checkNotNull(service, "personService must not be null");
        this.personService = service;
        this.personHierarchyService = personHierarchyService;
        this.userRoleService = userRoleService;
    }

    @PostMapping("/person-ids/directs")
    List<Person> getDirectsForPersonIds(@RequestBody List<Long> personIds) {

        return personService.findDirectsForPersonIds(personIds);
    }

    @GetMapping("/search/{query}")
    List<Person> search(@PathVariable String query) {
        return personService.search(query);
    }

    @GetMapping("/employee-id/{empId}/directs")
    List<Person> getDirectsForEmployeeId(@PathVariable String empId) {
        return personService.findDirectsByEmployeeId(empId);
    }

    @GetMapping("/employee-id/{empId}/count-cumulative-reports")
    Map<PersonKind, Integer> countCumulativeReports(@PathVariable String empId) {
        return personService.countAllUnderlingsByKind(empId);
    }

    @GetMapping("/employee-id/{empId}/managers")
    List<Person> getManagers(@PathVariable String empId) {
        return personService.findAllManagersByEmployeeId(empId);
    }

    @GetMapping("/employee-id/{empId}")
    Person getEmployee(@PathVariable String empId) {
        return personService.getByEmployeeId(empId);
    }

    @GetMapping("/user-id/{userId}")
    Person getUserById(@PathVariable String userId) {
        return personService.getPersonByUserId(userId);
    }

    @GetMapping("/self")
    Person getSelf(HttpServletRequest request) {
        return personService.getPersonByUserId(getUsernameForSB(request));
    }

    @GetMapping("/id/{id}")
    Person getById(@PathVariable Long id) {
        return personService.getById(id);
    }

    @GetMapping("/rebuild-hierarchy")
    Boolean reBuildHierarchy() {
        //TODO return  requireRole(userRoleService, request, SystemRole.ADMIN);
        personHierarchyService.build();
        return true;
    }

}
