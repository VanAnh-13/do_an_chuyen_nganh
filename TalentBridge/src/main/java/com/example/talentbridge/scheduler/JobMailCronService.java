package com.example.talentbridge.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.talentbridge.model.Subscriber;
import com.example.talentbridge.repository.SubscriberRepository;
import com.example.talentbridge.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class JobMailCronService {

    private final SubscriberRepository subscriberRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 8 * * ?")
    public void sendJobRecommendationToAllUsers() {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        int sent = 0, failed = 0;

        for (Subscriber subscriber : subscribers) {
            try {
                emailService.sendJobNotificationForSubscriber(subscriber);
                sent++;
            } catch (Exception ex) {
                failed++;
            }
        }

        log.info("Đã gửi job mail cho {} users, thất bại {}", sent, failed);
    }
}
