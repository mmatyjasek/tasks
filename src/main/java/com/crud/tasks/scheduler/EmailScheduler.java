package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private static final String SUBJECT = "Tasks: Once a day email";

    @Autowired
    SimpleEmailService emailService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    AdminConfig adminConfig;

    @Scheduled(cron = "0 0 8 * * *")
    public void sendInformationEmail() {
        long size = taskRepository.count();
        String task = (size != 1) ? "tasks" : "task";
        emailService.send(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                "You have " + size + " " + task + " in your database",
                "")
        );
    }
}