package com.CircleUp.CircleUp.controller;

import com.CircleUp.CircleUp.entity.Notification;
import com.CircleUp.CircleUp.entity.User;
import com.CircleUp.CircleUp.repository.NotificationRepository;
import com.CircleUp.CircleUp.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    @GetMapping
    public List<Notification>getUserNotifications(@AuthenticationPrincipal User user){
        return notificationService.getUserNotifications(user.getId());
    }
    @PutMapping("/{id}/read")
    public String markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return "Notification marked as read";
    }

}
