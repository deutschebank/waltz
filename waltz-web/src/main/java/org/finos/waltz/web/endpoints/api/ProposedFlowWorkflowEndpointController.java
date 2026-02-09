package org.finos.waltz.web.endpoints.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.finos.waltz.common.exception.FlowCreationException;
import org.finos.waltz.common.exception.InsufficientPrivelegeException;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.proposed_flow.ProposeFlowPermission;
import org.finos.waltz.model.proposed_flow.ProposedFlowActionCommand;
import org.finos.waltz.model.proposed_flow.ProposedFlowCommand;
import org.finos.waltz.model.proposed_flow.ProposedFlowCommandResponse;
import org.finos.waltz.model.proposed_flow.ProposedFlowResponse;
import org.finos.waltz.service.proposed_flow_workflow.ProposedFlowWorkflowService;
import org.finos.waltz.service.workflow_state_machine.exception.TransitionNotFoundException;
import org.finos.waltz.service.workflow_state_machine.exception.TransitionPredicateFailedException;
import org.finos.waltz.service.workflow_state_machine.proposed_flow.ProposedFlowWorkflowTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.service.workflow_state_machine.proposed_flow.ProposedFlowWorkflowTransitionAction.findByVerb;
import static org.finos.waltz.web.WebUtilities.mkPath;


@RestController
@RequestMapping(ProposedFlowWorkflowEndpointController.BASE_URL)
public class ProposedFlowWorkflowEndpointController {
    private static final Logger LOG = LoggerFactory.getLogger(ProposedFlowWorkflowEndpoint.class);
    public static final String BASE_URL = "/api/mc";
    private final ProposedFlowWorkflowService proposedFlowWorkflowService;

    @Autowired
    public ProposedFlowWorkflowEndpointController(ProposedFlowWorkflowService proposedFlowWorkflowService) {
        this.proposedFlowWorkflowService = proposedFlowWorkflowService;
    }
    
    @PostMapping("/propose")
    public ProposedFlowCommandResponse proposeNewFlow(Principal principal,
                                                      @RequestBody ProposedFlowCommand proposedFlowCommand) {
        return proposedFlowWorkflowService.proposeNewFlow(principal.getName(), proposedFlowCommand);
    }

    @GetMapping("/propose-flow/id/{id}")
    public ProposedFlowResponse getProposedFlowById(@PathVariable("id") long proposedFlowId) {
        return proposedFlowWorkflowService.getProposedFlowResponseById(proposedFlowId);
    }

    @PostMapping("/propose-flow")
    public List<ProposedFlowResponse> findProposedFlows(@RequestBody IdSelectionOptions selectionOptions) throws JsonProcessingException {
        return proposedFlowWorkflowService.getProposedFlows(selectionOptions);
    }

    @PostMapping("/{id}/{action}")
    public ProposedFlowResponse proposedFlowAction(Principal principal,
                                                   @PathVariable("id") long proposedFlowId,
                                                   @PathVariable("action") String action,
                                                   @RequestBody ProposedFlowActionCommand command) throws FlowCreationException, TransitionNotFoundException, TransitionPredicateFailedException {
        ProposedFlowWorkflowTransitionAction proposedFlowAction = checkNotNull(findByVerb(action), "Invalid action: %s", action);
        return proposedFlowWorkflowService.proposedFlowAction(proposedFlowId, proposedFlowAction, principal.getName(), command);
    }

    @GetMapping("/{entityKind}/{entityId}/permissions/user")
    public ProposeFlowPermission getUserPermissionsForEntityRef(Principal principal,
                                                                @PathVariable("entityKind") String entityKind,
                                                                @PathVariable("entityId") long entityId) throws InsufficientPrivelegeException {
        EntityReference entityRef = EntityReference.mkRef(EntityKind.valueOf(entityKind), entityId);
        return proposedFlowWorkflowService.getUserPermissionsForEntityRef(principal.getName(), entityRef);
    }
}
