package org.finos.waltz.web.endpoints.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.finos.waltz.model.ImmutableReleaseLifecycleStatusChangeCommand;
import org.finos.waltz.model.ReleaseLifecycleStatus;
import org.finos.waltz.model.ReleaseLifecycleStatusChangeCommand;
import org.finos.waltz.service.aggregate_overlay_diagram.AggregateOverlayDiagramService;
import org.finos.waltz.service.user.UserRoleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebMvcTest(AggregateOverlayDiagramEndpointController.class)
@ContextConfiguration(classes = {SecurityEnableConfig.class})
class AggregateOverlayDiagramEndpointTest {


    @MockitoBean
    private AggregateOverlayDiagramService mockDiagramService;
    @MockitoBean
    private UserRoleService mockUserRoleService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "testUser", roles = {"ADMIN"})
    void testUpdateStatusRoute() throws Exception {
        ReleaseLifecycleStatusChangeCommand command = ImmutableReleaseLifecycleStatusChangeCommand.builder().newStatus(ReleaseLifecycleStatus.ACTIVE).build();
        String payload = objectMapper.writeValueAsString(command);
        Mockito.when(mockDiagramService.updateStatus(123, command, "testUser"))
                .thenReturn(true);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/api/aggregate-overlay-diagram/id/{id}", 123)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andReturn()
                .getResponse();
        String responseBody = response.getContentAsString();

        assertEquals(response.getStatus(), 200);
        assertTrue(Boolean.parseBoolean(responseBody));
    }

}