package org.finos.waltz.web.endpoints.api;

import org.finos.waltz.model.tally.OrderedTally;
import org.finos.waltz.model.tally.Tally;
import org.finos.waltz.service.user_contribution.UserContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.common.StringUtilities.parseInteger;

@RestController
@RequestMapping("/api/user-contribution")
public class UserContributionEndpointController {


    private static final int DEFAULT_LIMIT = 10;

    private final UserContributionService userContributionService;


    @Autowired
    public UserContributionEndpointController(UserContributionService userContributionService) {
        checkNotNull(userContributionService, "userContributionService cannot be null");

        this.userContributionService = userContributionService;
    }

    @GetMapping("/leader-board")
    public List<OrderedTally<String>> getLeaderBoard(@RequestParam String limitStr) {
        int limit = parseInteger(limitStr, DEFAULT_LIMIT);

        return userContributionService.getLeaderBoard(limit);
    }

    @GetMapping("/user/{userId}/score")
    public Double getScoreForUser(@PathVariable("userId") String userId) {
        return userContributionService.getScoreForUser(userId);
    }

    @GetMapping("/user/{userId}/directs/score")
    public List<Tally<String>> findScoresForDirectReports(@PathVariable("userId") String userId) {
        return userContributionService.findScoresForDirectReports(userId);
    }

    @GetMapping("/monthly-leader-board")
    public List<OrderedTally<String>> getLeaderBoardLastMonth(@RequestParam String limitStr) {
        int limit = parseInteger(limitStr, DEFAULT_LIMIT);
        return userContributionService.getLeaderBoardLastMonth(limit);
    }

    @GetMapping("/user/{userId}/ordered-leader-board")
    public List<OrderedTally<String>> getRankedLeaderBoard(@PathVariable("userId") String userId) {
        return userContributionService.getRankedLeaderBoard(userId);
    }

}
