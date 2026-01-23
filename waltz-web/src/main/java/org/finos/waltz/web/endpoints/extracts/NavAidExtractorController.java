package org.finos.waltz.web.endpoints.extracts;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.finos.waltz.common.SvgUtilities;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.svg.SvgDiagram;
import org.finos.waltz.service.measurable.MeasurableService;
import org.finos.waltz.service.svg.SvgDiagramService;
import org.finos.waltz.web.WebUtilities;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.finos.waltz.common.StringUtilities.isNumericLong;
import static org.finos.waltz.common.StringUtilities.toOptional;
import static org.finos.waltz.model.EntityLinkUtilities.mkExternalIdLink;
import static org.finos.waltz.model.EntityLinkUtilities.mkIdLink;
import static org.finos.waltz.web.endpoints.extracts.DirectQueryBasedExtractorUtilities.writeExtract;


@RestController
@RequestMapping("/data-extract/nav-aid/")
public class NavAidExtractorController {

    @Value("${waltz.base.url:localhost}")
    private String baseUrl;

    private final MeasurableService measurableService;
    private final SvgDiagramService svgDiagramService;


    @Autowired
    public NavAidExtractorController(MeasurableService measurableService,
                                     SvgDiagramService svgDiagramService) {
        this.measurableService = measurableService;
        this.svgDiagramService = svgDiagramService;
    }


    @GetMapping("{svgDiagramId}")
    public Object getNavAid(@PathVariable("svgDiagramId") long diagramId,
                          HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        SvgDiagram diagram = svgDiagramService.getById(diagramId);
        String svgWithLinks = addHyperLinks(diagram);

        String suggestedFilename = diagram.name()
                .replace(".", "-")
                .replace(" ", "-")
                .replace(",", "-");

        return writeExtract(
                suggestedFilename,
                svgWithLinks.getBytes(),
                request,
                response);

    }


    private String addHyperLinks(SvgDiagram diagram) {
        Function<String, Optional<String>> keyToUrl = mkKeyToUrl(diagram.group());

        return Unchecked.supplier(() -> SvgUtilities.addWaltzEntityLinks(
                                            diagram.svg(),
                                            diagram.keyProperty(),
                                            keyToUrl))
                .get();
    }


    private Function<String, Optional<String>> mkKeyToUrl(String groupId) {
        if (groupId.startsWith("NAVAID.MEASURABLE.")) {
            String categoryIdStr = groupId.replace("NAVAID.MEASURABLE.", "");
            if (isNumericLong(categoryIdStr)) {
                return mkMeasurableKeyToUrl(Long.parseLong(categoryIdStr));
            }
        } else {
            switch (groupId) {
                case "DATA_TYPE":
                    return mkDataTypeKeyToUrl();
                case "ORG_UNIT":
                    return mkOrgUnitKeyToUrl();
                case "ORG_TREE":
                    return mkPersonKeyToUrl();
            }
        }

        return (key) -> Optional.empty();
    }


    private Function<String, Optional<String>> mkMeasurableKeyToUrl(Long categoryId) {
        Map<String, Long> extToIdMap = measurableService.findExternalIdToIdMapByCategoryId(categoryId);
        return (extId) -> Optional.ofNullable(extToIdMap.get(extId))
                                    .map(id -> mkIdLink(baseUrl, EntityKind.MEASURABLE, id));
    }


    private Function<String, Optional<String>> mkDataTypeKeyToUrl() {
        return (dtCode) -> Optional.ofNullable(dtCode)
                                    .map(dc -> mkExternalIdLink(baseUrl, EntityKind.DATA_TYPE, dc));
    }


    private Function<String, Optional<String>> mkOrgUnitKeyToUrl() {
        return (unitId) -> toOptional(isNumericLong(unitId)
                                            ? mkIdLink(baseUrl, EntityKind.ORG_UNIT, Long.valueOf(unitId))
                                            : null);
    }


    private Function<String, Optional<String>> mkPersonKeyToUrl() {
        return (empId) -> Optional.ofNullable(empId)
                                    .map(eId -> mkExternalIdLink(baseUrl, EntityKind.PERSON, eId));
    }
}
