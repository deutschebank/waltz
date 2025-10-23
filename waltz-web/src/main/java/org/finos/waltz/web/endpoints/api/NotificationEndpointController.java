package org.finos.waltz.web.endpoints.api;

import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.model.notification.NotificationResponse;
import org.finos.waltz.service.notification.NotificationService;
import org.finos.waltz.web.WebUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.finos.waltz.common.Checks.checkNotNull;

@RestController
@RequestMapping("/api/notification")
public class NotificationEndpointController {
    private static final Logger LOG = LoggerFactory.getLogger(NotificationEndpoint.class);
    private static final String BASE_URL = WebUtilities.mkPath("api", "notification");

    private final NotificationService notificationService;


    @Autowired
    public NotificationEndpointController(NotificationService notificationService) {
        checkNotNull(notificationService, "notificationService cannot be null");

        this.notificationService = notificationService;
    }

    @GetMapping
    NotificationResponse getNotificationsByUser(HttpServletRequest request) {
        return notificationService.getNotificationsByUserId(WebUtilities.getUsernameForSB(request));
    }
}
