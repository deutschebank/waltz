package org.finos.waltz.web;

import org.finos.waltz.web.endpoints.api.SecurityEnableConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TestController.class)
@ContextConfiguration(classes = {SecurityEnableConfig.class})
class GlobalExceptionHandlerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testNotFoundException() throws Exception {
        mockMvc.perform(get("/test/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not found exceptionFlow Classification Rule not found"))
                .andExpect(jsonPath("$.id").value("ASRM-NF"));
    }

    @Test
    void testNoDataFoundException() throws Exception {
        mockMvc.perform(get("/test/no-data-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not found exception for testing"))
                .andExpect(jsonPath("$.id").value("NO_DATA"));
    }

    @Test
    void testUpdateFailedException() throws Exception {
        mockMvc.perform(get("/test/update-failed"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Update failed exception: for testing"))
                .andExpect(jsonPath("$.id").value("E1"));
    }

    @Test
    void testDuplicateKeyException() throws Exception {
        mockMvc.perform(get("/test/duplicate-Key"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Duplicate detected:  for testing"))
                .andExpect(jsonPath("$.id").value("DUPLICATE"));
    }

    @Test
    void testDataIntegrityViolationException() throws Exception {
        mockMvc.perform(get("/test/dataIntegrity-violation"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Data integrity violation detected:  for testing"))
                .andExpect(jsonPath("$.id").value("DATA_INTEGRITY"));
    }

    @Test
    void testDataAccessException() throws Exception {
        mockMvc.perform(get("/test/data-access"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Data Access Exception: java.lang.Throwable: for testing [org.jooq.exception.DataAccessException]"))
                .andExpect(jsonPath("$.id").value("00000"));
    }

    @Test
    void testIllegalArgumentException() throws Exception {
        mockMvc.perform(get("/test/illegal-argument"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Illegal Argument Exception:  for testing"))
                .andExpect(jsonPath("$.id").value("ILLEGAL ARGUMENT"));
    }

    @Test
    void testWebException() throws Exception {
        mockMvc.perform(get("/test/web"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Web exception:  for testing"))
                .andExpect(jsonPath("$.id").value("E1"));
    }

    @Test
    void testNotAuthorizedException() throws Exception {
        mockMvc.perform(get("/test/not-authorized"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Not Authorized Exception for testing"))
                .andExpect(jsonPath("$.id").value("NOT_AUTHORIZED"));
    }

    @Test
    void testInsufficientPrivilegeException() throws Exception {
        mockMvc.perform(get("/test/insufficient-privilege"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("User cannot modify note"))
                .andExpect(jsonPath("$.id").value("NOT_AUTHORIZED"));
    }

    @Test
    void testGenericException() throws Exception {
        mockMvc.perform(get("/test/generic-exception"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Generic Exception: Generic Exception / java.lang.Exception"))
                .andExpect(jsonPath("$.id").value("unknown"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testHandleIllegalArgumentExceptionSecurity() throws Exception {
        mockMvc.perform(get("/test/not-found/security"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("You do not have permission to perform that operation"))
                .andExpect(jsonPath("$.id").value("NOT_AUTHORIZED"));
    }

}