package org.finos.waltz.web.endpoints.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import org.finos.waltz.model.measurable_category.ImmutableMeasurableCategory;
import org.finos.waltz.model.measurable_category.MeasurableCategory;
import org.finos.waltz.service.measurable_category.MeasurableCategoryService;
import org.finos.waltz.web.WebUtilities;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(MeasurableCategoryEndpointController.class)
@ContextConfiguration(classes = {SecurityDisabledConfig.class})
class MeasurableCategoryEndpointTest {

    @MockitoBean
    private MeasurableCategoryService mockService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testGetAllCategories() throws Exception {

        Mockito.when(mockService.findAll()).thenReturn(
                Arrays.asList(
                        ImmutableMeasurableCategory.builder()
                                .id(1L).name("Risk").description("Risk-Related").externalId("risk-category")
                                .lastUpdatedBy("testUser").icon("testIcon").ratingSchemeId(1).allowPrimaryRatings(false).build(),
                        ImmutableMeasurableCategory.builder()
                                .id(2L).name("Compliance").description("Compliance-Related").externalId("compliance-category")
                                .lastUpdatedBy("testUser").icon("testIcon").ratingSchemeId(1).allowPrimaryRatings(false).build()
                )
        );

        MockHttpServletResponse response = mockMvc.perform(get("/api/measurable-category/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        String responseBody = response.getContentAsString();

        assertEquals(response.getStatus(), 200);
        assertThat(JsonPath.read(responseBody, "$.length()"), equalTo(2));
        assertThat(JsonPath.read(responseBody, "$[0].id"), equalTo(1));
        assertThat(JsonPath.read(responseBody, "$[0].name"), equalTo("Risk"));
        assertThat(JsonPath.read(responseBody, "$[0].description"), equalTo("Risk-Related"));
        assertThat(JsonPath.read(responseBody, "$[1].id"), equalTo(2));
        assertThat(JsonPath.read(responseBody, "$[1].name"), equalTo("Compliance"));
        assertThat(JsonPath.read(responseBody, "$[1].description"), equalTo("Compliance-Related"));
        verify(mockService, times(1)).findAll();
    }


    @Test
    public void testSaveCategory() throws Exception {

        Long mockId = 42L;
        String mockUsername = "testUser";


        MeasurableCategory measurableCategory = ImmutableMeasurableCategory.builder()
                .name("Finance")
                .description("Handles finance-related activities")
                .externalId("FINANCE-001")
                .icon("testIcon")
                .ratingSchemeId(1)
                .allowPrimaryRatings(true)
                .lastUpdatedBy(mockUsername)
                .build();
        try (MockedStatic<WebUtilities> webUtilitiesMock = mockStatic(WebUtilities.class)) {

            webUtilitiesMock.when(() -> WebUtilities.getUsernameForSB(any()))
                    .thenReturn(mockUsername);

            when(mockService.save(any(MeasurableCategory.class), eq(mockUsername)))
                    .thenReturn(mockId);


            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.registerModule(new Jdk8Module());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String requestBody = objectMapper.writeValueAsString(measurableCategory);

            MockHttpServletResponse response = mockMvc.perform(post("/api/measurable-category/save")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("username", mockUsername)
                            .content(requestBody))
                    .andReturn()
                    .getResponse();
            String responseBody = response.getContentAsString();

            assertEquals(response.getStatus(), 200);
            assertEquals(Long.parseLong(responseBody), mockId);

            verify(mockService, times(1)).save(
                    argThat(matchesMeasurableCategory(measurableCategory)),
                    eq(mockUsername)
            );
            verifyNoMoreInteractions(mockService);
        }
    }

    private ArgumentMatcher<MeasurableCategory> matchesMeasurableCategory(MeasurableCategory expected) {
        return actual -> expected.name().equals(actual.name())
                && expected.description().equals(actual.description())
                && expected.externalId().equals(actual.externalId())
                && expected.lastUpdatedBy().equals(actual.lastUpdatedBy());
    }
}