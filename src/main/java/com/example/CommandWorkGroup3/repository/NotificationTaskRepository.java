package com.example.CommandWorkGroup3.repository;

import com.example.CommandWorkGroup3.entity.NotificationTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {

    List<NotificationTask> findNotificationTaskByDate(LocalDateTime date);
}
