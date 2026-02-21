package com.CircleUp.CircleUp.service;

import com.CircleUp.CircleUp.entity.Notification;
import com.CircleUp.CircleUp.entity.User;
import com.CircleUp.CircleUp.repository.NotificationRepository;
import com.CircleUp.CircleUp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public void send(Long userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setReadStatus(false);
        notification.setCreatedAt(
                LocalDateTime.now());

        notificationRepository.save(notification);
    }


    public List<Notification>getUserNotifications(Long userId){
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    public void markAsRead(Long notifiId){
        Notification notification=notificationRepository.findById(notifiId).orElseThrow(()->new RuntimeException("Notification not found!!"));
        notification.setReadStatus(true);
        notificationRepository.save(notification);
    }
}
