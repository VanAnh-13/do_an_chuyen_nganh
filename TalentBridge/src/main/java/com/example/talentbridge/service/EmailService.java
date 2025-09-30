package com.example.talentbridge.service;

import jakarta.mail.MessagingException;
import com.example.talentbridge.model.Subscriber;


public interface EmailService {

    void sendJobNotificationForSubscriber(Subscriber subscriber) throws MessagingException;

    void sendJobNotificationManually(String email) throws MessagingException;
}
