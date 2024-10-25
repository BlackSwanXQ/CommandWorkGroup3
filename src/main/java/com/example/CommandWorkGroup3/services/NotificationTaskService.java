package com.example.CommandWorkGroup3.services;

import com.example.CommandWorkGroup3.entity.NotificationTask;
import com.example.CommandWorkGroup3.repository.NotificationTaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationTaskService {
    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @Transactional
    public void save(String text, long chatId, LocalDateTime notificationDateTime) {
        NotificationTask notificationTask = new NotificationTask();
        notificationTask.setDate(notificationDateTime);
        notificationTask.setChatID(chatId);
        notificationTask.setNotification(text);
        notificationTaskRepository.save(notificationTask);
    }

}
